package ru.shvets.myappretrofit.view.news.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import ru.shvets.myappretrofit.MainActivity
import ru.shvets.myappretrofit.databinding.FragmentArticleDetailsBinding
import ru.shvets.myappretrofit.view.news.NewsViewModel
import ru.shvets.myappretrofit.view.news.article.ArticleFragmentArgs

class ArticleDetailsFragment : Fragment() {
    private var _binding: FragmentArticleDetailsBinding? = null
    private val mBinding
        get() = _binding

    //    private val viewModel: NewsViewModel by viewModels()
    private lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    private val article
        get() = args.article

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        mBinding?.let { binding ->
            binding.apply {

                article.urlToImage?.let {
                    Picasso.get().load(it).into(imageViewImage)
//                    Glide.with(this@ArticleDetailsFragment).load(it).into(imageViewImage)
                }

                imageViewImage.clipToOutline = true
                textViewTitle.text = article.title
                textViewDescriptionText.text = article.description

                btnArticleDetails.setOnClickListener {
                    try {

                        Intent()
                            .setAction(Intent.ACTION_VIEW)
                            .addCategory(Intent.CATEGORY_BROWSABLE)
                            .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url) }?.let {
                                article.url
                            } ?: "http://google.com"))
                            .let {
                                ContextCompat.startActivity(requireContext(), it, null)
                            }

                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "The device doesn't have any browser to view the document",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                btnLoadArticle.setOnClickListener {
                    val direction = ArticleDetailsFragmentDirections
                        .actionArticleDetailsFragmentToArticleFragment(article)
                    findNavController().navigate(direction)
                }

                imageViewFavorite.setOnClickListener {
                    viewModel.saveArticle(article)
                }

                imageViewShare.setOnClickListener {

                }
            }
        }

    }
}
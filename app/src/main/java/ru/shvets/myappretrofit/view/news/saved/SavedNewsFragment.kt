package ru.shvets.myappretrofit.view.news.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.shvets.myappretrofit.MainActivity
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.data.news.Article
import ru.shvets.myappretrofit.databinding.FragmentSavedNewsBinding
import ru.shvets.myappretrofit.view.news.NewsActionListener
import ru.shvets.myappretrofit.view.news.NewsAdapter
import ru.shvets.myappretrofit.view.news.NewsViewModel

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    private lateinit var _binding: FragmentSavedNewsBinding
    private val mBinding get() = _binding
//    private lateinit var actionBar: ActionBar

    //    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(layoutInflater, container,false)
//        actionBar = (activity as AppCompatActivity).supportActionBar!!
//        actionBar.setTitle(R.string.title_weather)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val article = newsAdapter.differ.currentList[position]
//                viewModel.deleteArticle (article)
                article.id?.let { viewModel.deleteArticleById(it) }
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(mBinding.recyclerViewSavedNews)
        }

        viewModel.getAllSavedNewsArticle().observe(viewLifecycleOwner, Observer { list ->
            newsAdapter.differ.submitList(list)
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(object : NewsActionListener{
            override fun onItemClicked(article: Article) {
            }

            override fun onLikeClicked(article: Article) {
            }

            override fun onShareClicked(article: Article) {
            }

        })

        mBinding.recyclerViewSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)

            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }
}
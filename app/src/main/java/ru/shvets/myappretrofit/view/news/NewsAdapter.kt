package ru.shvets.myappretrofit.view.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.shvets.myappretrofit.R
import ru.shvets.myappretrofit.data.news.Article
import ru.shvets.myappretrofit.databinding.ItemArticlePreviewBinding

class NewsAdapter(
    private val listener: NewsActionListener
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(), View.OnClickListener {

    inner class NewsViewHolder(
        private val binding: ItemArticlePreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                imageViewShare.tag = article
                imageViewFavorite.tag = article
                root.tag = article

                Picasso.get().load(article.urlToImage).into(imageViewArticleImage)
//                Glide.with(itemView).load(article.urlToImage).into(imageViewArticleImage)
                imageViewArticleImage.clipToOutline = true
                textViewTitle.text = article.title
                textViewPublishedAt.text = article.publishedAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticlePreviewBinding.inflate(inflater, parent, false)

        binding.apply {
            imageViewShare.setOnClickListener(this@NewsAdapter)
            imageViewFavorite.setOnClickListener(this@NewsAdapter)
            root.setOnClickListener(this@NewsAdapter)
        }

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onClick(view: View) {
        val article = view.tag as Article

        when (view.id) {
            R.id.image_view_share -> listener.onLikeClicked(article)
            R.id.image_view_favorite -> listener.onItemClicked(article)
            else -> listener.onItemClicked(article)
        }
    }
}
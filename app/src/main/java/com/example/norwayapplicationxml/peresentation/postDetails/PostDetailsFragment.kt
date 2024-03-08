package com.example.norwayapplicationxml.peresentation.postDetails

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.norwayapplicationxml.R
import com.example.norwayapplicationxml.common.Constants
import com.example.norwayapplicationxml.common.Resource
import com.example.norwayapplicationxml.databinding.FragmentPostDeatilsBinding
import com.example.norwayapplicationxml.domain.model.New
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle
import java.time.format.FormatStyle
import java.util.Locale
import java.util.regex.Pattern


private const val TAG = "PostDetailsFragment"

private const val LINK = "link"
class PostDetailsFragment : Fragment() {
    lateinit var binding: FragmentPostDeatilsBinding
    private val viewModel : PostDetailsViewModelImpl by viewModels()
    lateinit var new : New
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new = requireArguments().getSerializable(LINK) as New
        viewModel.getPostDetails(new.link)

        val trans = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        sharedElementEnterTransition = trans
        sharedElementReturnTransition = trans

        binding.title.text = new.title
        setMetaDataDetails(new)
        Glide.with(requireContext()).load(new.image)
            .centerCrop()
            .into(binding.postMainImage)


        viewModel.getNew {
            when (it) {
                is Resource.failed -> {
                    binding.title.text = it.messaage.message!!
                }
                is Resource.success -> {
                    Log.d(TAG, "onViewCreated: ${it.data}")
                    binding.title.text = it.data.title
                    Glide.with(requireContext()).load(it.data.image)
                        .centerCrop()
                        .into(binding.postMainImage)

                    setMetaDataDetails(it.data)

                    binding.shimmerLayout.isVisible = false
                    binding.articleContent.isVisible = true

                    Glide.with(requireContext()).load(it.data.publisher?.title)
                        .transform(MultiTransformation(
                            CenterCrop(),
                            RoundedCorners(14)
                        ))
                        .into(binding.publisherImage)
                    binding.likes.isVisible = true
                    binding.likes.text = it.data.likes.toString()
                    binding.articleContent.text = it.data.articleContent.map { it.replace("," , "") }.filter { it.isNotEmpty() }.joinToString("") { it + "\n" }

                    binding.footerLayout.isVisible = true
//                    binding.publisher.movementMethod = LinkMovementMethod.getInstance()
//                    val ptn = Pattern.compile("\\b[A-Z]+[a-z0-9]+[A-Z][A-Za-z0-9]+\\b")
//                    val pubLink = it.data.publisher?.link
//                    Linkify.addLinks(binding.publisher , ptn , pubLink)
//                    binding.publisher.text =Html.fromHtml("&lt;a href=\"${it.data.publisher?.link}\">${it.data.footerContent.get(0).replace(",", "")}&lt;/a>")
                    binding.footerMeta.text = it.data.footerContent.map { it.replace("," , "") }.filter { it.isNotEmpty() }.joinToString(separator = "", "" , ""){ it + "\n"}
                    binding.youtubeChannel.movementMethod = LinkMovementMethod.getInstance()
                    binding.youtubeChannel.text = Html.fromHtml("<a href=\"${it.data.youtubeLink?.link}\">${it.data.youtubeLink?.title}</a>")

                }
            }
            Log.d(TAG, "onViewCreated: newDetails: $it")
        }
    }

    private fun setMetaDataDetails(data: New) {
        if (data.date == ""){
            binding.postMetaDetails.text =
                String.format(
                    getString(R.string.published_in_s_s_s_views_by_s),
                    "",
                    data.readDuration,
                    data.votes,
                    data.by
                )
        }else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val dateInArabic =
                        SimpleDateFormat(Constants.dateFormate).parse(data.date)
                    val arabicLocale = Locale.forLanguageTag("ar")
                    val arabicDateFormatter =
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                            .withLocale(arabicLocale)
                            .withDecimalStyle(DecimalStyle.of(arabicLocale))
                    val date = LocalDateTime.from(
                        dateInArabic.toInstant().atZone(ZoneId.systemDefault())
                    ).format(arabicDateFormatter)
                    binding.postMetaDetails.text =
                        String.format(
                            getString(R.string.published_in_s_s_s_views_by_s),
                            date,
                            data.readDuration,
                            data.votes,
                            data.by
                        )
                }
            }catch (e:Exception){
                binding.postMetaDetails.text =
                    String.format(
                        getString(R.string.published_in_s_s_s_views_by_s),
                        data.date,
                        data.readDuration,
                        data.votes,
                        data.by
                    )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDeatilsBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object{
        fun getInstance(new:New) = PostDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(LINK , new)
            }
        }
    }
}
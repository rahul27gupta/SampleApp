import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sampleapp.R

object SampleAppUtils {

    @JvmStatic
    fun ImageView.loadImage(url: String?) {
        url?.let {
            Glide.with(context)
                .load(it)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(this)
        }
    }

    @JvmStatic
    fun TextView.setDescription(episode: String?, rating: String?) {
        val parts = listOfNotNull(
            episode?.takeIf { it.isNotBlank() }?.plus(" Episodes"),
            rating?.takeIf { it.isNotBlank() }
        )
        text = parts.joinToString(" · ")
    }

}
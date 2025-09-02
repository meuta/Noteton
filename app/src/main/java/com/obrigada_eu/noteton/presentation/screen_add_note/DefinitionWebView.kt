package com.obrigada_eu.noteton.presentation.screen_add_note

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun DefinitionWebView(
    htmlContent: String,
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = false

                // To make selectable:
                isLongClickable = true
                setOnLongClickListener(null)
                isHapticFeedbackEnabled = true
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL(
                null,
                htmlContent,
                "text/html",
                "UTF-8",
                null
            )
        }
    )
}



class HtimlStringPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
"<html><body style='font-family: sans-serif;'><div style='margin-bottom:1em;'><b>Saudade</b><p style='margin:0.5em 0;'><b>1.</b> f. <br/>Lembrança triste e suave de pessoas ou coisas distantes ou extintas, acompanhada do desejo de as tornar a possuir ou ver presentes.<br/>Pesar, pela ausência de alguém que nos é querido.<br/>Nostalgia.</p><p style='margin:0.5em 0;'><b>2.</b> <i>Bot.</i>  <br/>Nome de várias plantas ou da sua flor.</p><p style='margin:0.5em 0;'><b>3.</b> <i>Bras. de Pernambuco</i>  <br/>Planta, o mesmo que <i>oficial-de-sala</i>.</p><p style='margin:0.5em 0;'><b>4.</b> Pl. <br/>Cumprimentos, lembranças afetuosas, dirigidas a pessoas ausentes: <i>dê-lhe saudades</i>.<br/>(Alter, de <i>soidade</i>, de <i>soledade</i>)</p></div><hr/></body></html>"
    )
}


@Preview(showBackground = true)
@Composable
fun DefinitionWebViewPreview(
    @PreviewParameter (HtimlStringPreviewParameterProvider::class) htmlContent: String
) {
    Text(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        text = HtmlCompat.fromHtml(
            htmlContent,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        ).toString(),
        style = MaterialTheme.typography.bodyMedium
    )
}

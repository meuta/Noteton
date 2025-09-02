package com.obrigada_eu.noteton.data.remote.repository

import android.util.Log
import com.obrigada_eu.noteton.data.remote.api.DictionaryApi
import com.obrigada_eu.noteton.domain.repository.DictionaryRepository
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class DictionaryRepositoryImpl(
    private val api: DictionaryApi
) : DictionaryRepository {


    override suspend fun getWordDefinitionHtml(word: String): String? {
        val entries = api.getWordEntries(word).orEmpty()
        if (entries.isEmpty()) return null

        val htmlParts = entries.mapNotNull { it.xml?.let { xml -> xmlToHtml(xml) } }

        if (htmlParts.isEmpty()) return null

        return buildString {
            append("<html><body style='font-family: sans-serif;'>")
            htmlParts.forEach { part ->
                append(part)
                append("<hr/>")
            }
            append("</body></html>")
        }
    }


    private fun xmlToHtml(xml: String): String {
        val doc = Jsoup.parse(xml, Parser.xmlParser())

        val word = doc.selectFirst("orth")?.text().orEmpty()
        val senses = doc.select("sense")

        val sb = StringBuilder()
        sb.append("<div style='margin-bottom:1em;'>")
        sb.append("<b>").append(word).append("</b>")

        senses.forEachIndexed { idx, sense ->

            sb.append("<p style='margin:0.5em 0;'>")

            sb.append("<b>").append(idx + 1).append(".</b> ")

            val gram = sense.selectFirst("gramGrp")?.wholeText()
            gram?.let { sb.append(putItalic(it)).append(" ") }

            val usgList = sense.select("usg")
            val usg = usgList.joinToString(" ") { it.text() }.ifBlank { null }
            usg?.let { sb.append(italicOrEmpty(it)).append(" ") }

            val defRaw = sense.selectFirst("def")?.wholeText().orEmpty()
            val defTrimmed = defRaw.trimEnd('\n', '\r')
            val defHtml = defTrimmed
                .escapeHtml()
                .replace("\r\n", "\n")
                .replace("\n", "<br/>")

            sb.append(putItalic(defHtml))

            sb.append("</p>")
        }

        val etym = doc.selectFirst("etym")?.wholeText()
        etym?.let { sb.append(putItalic(it)) }

        sb.append("</div>")
        return sb.toString()
    }


    private fun putItalic(s: String): String {
        val ital = s
            .trim()
            .replace("_([^_]+)_".toRegex(), "<i>$1</i>")
        Log.d(TAG, "putItalic: ital = $ital")
        return ital
    }

    private fun italicOrEmpty(s: String?) =
        s?.takeIf { it.isNotBlank() }
        ?.trim()
        ?.escapeHtml()
        ?.let { "<i>$it</i> " }
        .orEmpty()

    private fun String.escapeHtml(): String =
        this.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")

    companion object {
        private const val TAG = "DictionaryRepositoryImpl"
    }
}

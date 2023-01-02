package br.com.astrosoft.devolucao.model.pdftxt

import br.com.astrosoft.framework.util.unaccent
import java.text.DecimalFormat
import kotlin.math.absoluteValue

private val titleWord = listOf("Cod", "Codigo", "Descricao", "Qtde", "Un", "Item", "Produto", "Qtd", "Preco", "Total")

data class Line(val num: Int, val lineStr: String, val fileText: FileText, val double: Boolean = false) {
  private val form1 = DecimalFormat("#,##0.#####")
  private val form2 = DecimalFormat("0.#####")

  fun find(text: String?, sepSplit: Regex): Boolean {
    return findIndex(text, sepSplit).isNotEmpty()
  }

  private fun listPosTokens(sepSplit: Regex): Sequence<LinePosition> {
    val seq = sequence {
      val tokens = lineStr.unaccent().split(sepSplit)
      var index = 0
      tokens.forEach { token ->
        index = " $lineStr ".indexOf(" $token ", startIndex = index)
        val pos = createLinePosition(index, token)
        if (pos != null) yield(pos)
        index += token.length
      }
    }
    return seq
  }

  private fun findIndex(text: String?, sepSplit: Regex): List<LinePosition> {
    text ?: return emptyList()
    val seq = listPosTokens(sepSplit).filter { it.text == text.unaccent() }
    return seq.toList()
  }

  private fun createLinePosition(start: Int, text: String): LinePosition? {
    val startIndex = 0
    val endIndex = lineStr.length - 1
    return if (start in startIndex..endIndex) {
      LinePosition(start, text.trim())
    }
    else null
  }

  private fun midLine(index: Int): String {
    val startIndex = 0
    val endIndex = lineStr.length - 1
    return if (index in startIndex..endIndex) {
      lineStr.substring(index)
    }
    else ""
  }

  private fun midLine(start: Int?, end: Int?): String? {
    start ?: return null
    end ?: return null
    val startIndex = 0
    val endIndex = if (end > lineStr.length) lineStr.length else end
    return if (start in startIndex..endIndex) {
      lineStr.substring(start, end)
    }
    else ""
  }

  fun item() =  listPosTokens(split2).toList().getOrNull(0)?.text ?: ""

  fun find(num: Number?): Boolean {
    return findIndex(num).toList().isNotEmpty()
  }

  fun findIndex(num: Number?): List<LinePosition> {
    num ?: return emptyList()
    val numStr = listPosTokens(split1).mapNotNull { pos ->
      val str = pos.text
      val number = strToNumber(str)
      if (number == null) null
      else {
        val dif = (num.toDouble() - number.toDouble()).absoluteValue
        if (dif <= 0.05) str
        else null
      }
    }.distinct()
    return numStr.flatMap { str -> findIndex(str, split1) }.toList()
  }

  val countTitleWord = titleWord.count { find(it, split2) }

  fun mid(start: Int, end: Int): String {
    return if (lineStr == "") ""
    else {
      val pstart = if (start < 1) 1 else start
      val pend = if (end > lineStr.length) lineStr.length else end
      if (pstart > lineStr.length) ""
      else if (pend < 1) ""
      else lineStr.substring(startIndex = pstart - 1, endIndex = pend)
    }
  }

  fun mid(start: Int): String = mid(start, start)

  fun tituloColuna(): Sequence<String> {
    return listPosTokens(split2).map { it.text }
  }

  fun columns(): List<Column> {
    val titles = this.tituloColuna().toList()
    var posIndex = 0
    val sequence = sequence<Column> {
      titles.forEachIndexed { index, title ->
        val isFirst = index == 0
        val isLast = index == (titles.toList().size - 1)
        val posStart = if (isFirst) 0
        else {
          val prevTitle = titles[index - 1]
          lineStr.indexOf(string = prevTitle, startIndex = posIndex) + prevTitle.length + 1
        }
        val posEnd = if (isLast) lineStr.length
        else {
          val nextTitle = titles[index + 1]
          lineStr.indexOf(string = nextTitle, startIndex = posStart + title.length - 1)
        }
        val col = Column(line = this@Line, numCol = index + 1, title = title, start = posStart, end = posEnd)
        yield(col)
        posIndex = posStart
      }
    }
    return sequence.toList()
  }

  private fun strToNumber(str: String?): Number? {
    str ?: return null
    val regNumPoint = "\\.[0-9]{3}[^0-9]".toRegex()
    val num01 = if (regNumPoint.containsMatchIn(str)) str.replace(".", "") else str
    return num01.trim().replace(',', '.').toDoubleOrNull()
  }

  fun getInt(start: Int?, end: Int?): Int? {
    start ?: return null
    end ?: return null
    val strInt = midLine(start = posStart(start), end = posEnd(end))?.split(split1)?.getOrNull(0)
    val int = strToNumber(strInt)?.toInt()
    return int
  }

  fun getDouble(start: Int?, end: Int?): Double? {
    start ?: return null
    end ?: return null
    val strDouble = midLine(start = posStart(start), end = posEnd(end))?.split(split1)?.getOrNull(0)
    val double = strToNumber(strDouble)?.toDouble()
    return double
  }

  private fun midChar(index: Int): Char? {
    return lineStr.getOrNull(index)
  }

  private fun posEnd(end: Int): Int? {
    return if(midChar(end) == ' ') end
    else {
      val token = listPosTokens(split1)
      token.map { it.end }.filter { it >= end }.minOrNull()
    }
  }

  private fun posStart(start: Int): Int? {
    return if(midChar(start) == ' ') start
    else {
      val token = listPosTokens(split1)
      token.map { it.start }.filter { it <= start }.maxOrNull()
    }
  }

  companion object{
    val split1 = "\\s+".toRegex()
    val split2 = "\\s\\s+".toRegex()
  }
}

data class LinePosition(val start: Int, val text: String) {
  val end
    get() = start + text.length
  val mean
    get() = (end + start) * 1.00 / 2.00
}
import scala.util.Random
import scala.math.{min}
import com.devskiller.jfairy.producer.text.TextProducer

object MarkdownIpsum extends SeederTrait {
  private final def hasContent(): Boolean     = biasedCoinFlip(probability = 0.75)
  private final def numberOfParagraphs(): Int = Random.between(1, 3)
  private final def numberOfSentences(): Int  = Random.between(1, 7)
  private final def hasHeading(): Boolean     = biasedCoinFlip(probability = 0.5)
  private final def hasEmphasis(): Boolean =
    biasedCoinFlip(probability = 0.33)
  private final def isItalicVersusBold(): Boolean =
    biasedCoinFlip(probability = 0.5)

  private final def emphasiseSentence(sentence: String): String =
    if (hasEmphasis()) {
      def addEmphasis(text: String) = {
        val emphasis: String = if (isItalicVersusBold()) {
          "*"
        } else {
          "**"
        }
        emphasis + text + emphasis
      }

      val tokenisedSentence: List[String] =
        sentence.split(" ").filter(_.nonEmpty).toList

      val emphasisedSentence: List[String] = {
        val sentenceLength: Int = tokenisedSentence.length
        val replacedLength: Int = Random.between(1, min(3, sentenceLength))
        val replacedIndex: Int =
          Random.between(1, sentenceLength - replacedLength)

        def emphasiseTokens(tokens: List[String]): List[String] = List(
          addEmphasis(tokens.mkString(" "))
        )

        tokenisedSentence.patch(
          replacedIndex,
          emphasiseTokens(
            tokenisedSentence
              .slice(replacedIndex, replacedIndex + replacedLength)
          ),
          replacedLength
        )
      }

      emphasisedSentence.mkString(" ")
    } else {
      sentence
    }

  final def apply(textProducer: TextProducer): String = {
    new String
    // if (hasContent()) {
    //   val hasHeadings: Boolean = hasHeading()
    //
    //   (1 to numberOfParagraphs)
    //     .map(paragraph => {
    //       val heading: Option[String] = Option
    //         .when(hasHeadings) { s"## ${textProducer.latinSentence(1)}" }
    //
    //       val content: String =
    //         emphasiseSentence(textProducer.latinSentence(numberOfSentences()))
    //
    //       heading ++ List(content)
    //     })
    //     .flatten
    //     .mkString("\n\n")
    //
    // } else {
    //   new String
    // }
  }
}

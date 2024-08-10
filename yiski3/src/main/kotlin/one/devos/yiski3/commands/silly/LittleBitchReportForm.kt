package one.devos.yiski3.commands.silly

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.send
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.PathsHelper
import xyz.artrinix.aviation.annotations.Name
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.awt.*
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@YiskiModule
class LittleBitchReportForm : Scaffold {
    @SlashCommand(name = "lbr", description = "The Little Bitch Report Form, for when someones being a little bitch.")
    suspend fun lbrf(
        ctx: SlashContext,
        @Description("Who are you reporting?") reporting: Member,
        @Description("Do they require medical assistance / tissues?") assistance: Boolean,
        @Description("On a scale of 1-10, how much do you think their butthole hurts?") scale: Int,
        @Description("Have they ever considered not being a little bitch?") consideration: Boolean,
        @Description("Does reporting make them moist") moist: Boolean,
        // this is so unsightly, and it's because of discords very limited options, and we can't do a rich modal or anything nice like a good enum either.
        @Description("Did they lose an argument?") @Name("argument-lost") argumentLost: Boolean? = false,
        @Description("Did they think their opinion was wrong?") @Name("wrong-opinion") wrongOpinion: Boolean? = false,
        @Description("Did they find something offensive and can't move on?") @Name("offensive-cant-move-on") offensiveCantMoveOn: Boolean? = false,
        @Description("Did they have 0 sense of humor") @Name("no-bitches") noBitches: Boolean? = false,
        @Description("Did they complain no one would suck their dick?") @Name("dick-less") dickLessBehavior: Boolean? = false,
        @Description("Did they complain that someone was being rude back to them?") @Name("collateral-damage") collateralDamage: Boolean? = false,
        @Description("Did they have nothing better to do") bored: Boolean? = false,
        @Description("Any additional details about the bitching?") details: String? = ""
    ) {
        val interaction = ctx.interaction.deferReply()
            .mentionUsers(reporting.idLong)
            .await()

        val bufferedImage: BufferedImage = withContext(Dispatchers.IO) {
            ImageIO.read(PathsHelper.filePath.path(PathsHelper.FileType.IMAGE, "lbrf.png"))
        }

        val font: Font = Font("Comic Sans MS", Font.TRUETYPE_FONT, 18)
        val graphic: Graphics2D = bufferedImage.createGraphics()
        val highlightingComposite: AlphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F)
        val highlightColour = Color(255, 255, 58, 200)
        // set antialiasing
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        // set comic fuckin sans
        graphic.font = font

        // set drawing brush to be black
        graphic.color = Color(0, 0, 0)

        // draw name
        graphic.drawString(reporting.user.name, 128, 270)

        // draw date
        graphic.drawString(java.time.LocalDate.now().toString(), 470, 270)

        if (argumentLost == true) {
            graphic.color = highlightColour
            graphic.composite = highlightingComposite
            graphic.fillRect(93, 344, 16, 16)
        }

        if (wrongOpinion == true) {
            graphic.color = highlightColour
            graphic.composite = highlightingComposite
            graphic.fillRect(93, 367, 16, 15)
        }

        if (offensiveCantMoveOn == true) {
            graphic.color = highlightColour
            graphic.composite = highlightingComposite
            graphic.fillRect(93, 398, 16, 16)
            graphic.color = Color(255, 0, 0, 150)
            graphic.fillOval(427, 335, 230, 230)
        }

        if (noBitches == true) {
            graphic.color = highlightColour
            graphic.composite = highlightingComposite
            graphic.fillRect(93, 429, 16, 16)
        }

        if (dickLessBehavior == true) {
            graphic.color = highlightColour
            graphic.composite = highlightingComposite
            graphic.fillRect(93, 452, 16, 15)
        }

        if (collateralDamage == true) {
            graphic.color = highlightColour
            graphic.composite = highlightingComposite
            graphic.fillRect(93, 474, 16, 16)
        }

        if (bored == true) {
            graphic.color = highlightColour
            graphic.composite = highlightingComposite
            graphic.fillRect(93, 497, 16, 16)
        }

        //<editor-fold desc="Draw assistance">
        graphic.color = highlightColour
        graphic.composite = highlightingComposite
        graphic.fillRect(
            if (assistance) 384 else 425,
            524,
            if (assistance) 40 else 30,
            30
        )
        //</editor-fold>

        // draw 1-10 scale highlight
        //<editor-fold desc="Draw scale">
        val stupidOffset = 24

        val scalePosition = 384 + when (scale) {
            2 -> stupidOffset
            in 3..9 -> stupidOffset * (scale - 1)
            10 -> (stupidOffset * 9) + 4
            else -> 0
        }

        graphic.color = highlightColour
        graphic.composite = highlightingComposite
        graphic.fillRect(scalePosition, 576, 21, 30)
        //</editor-fold>

        //<editor-fold desc="Draw consideration highlight">
        graphic.color = highlightColour
        graphic.composite = highlightingComposite
        graphic.fillRect(
            if (consideration) 384 else 425,
            630,
            if (consideration) 40 else 30,
            30
        )
        //</editor-fold>

        //<editor-fold desc="Draw moist highlight">
        graphic.color = highlightColour
        graphic.composite = highlightingComposite
        graphic.fillRect(
            if (moist) 384 else 425,
            670,
            if (moist) 40 else 30,
            30
        )
        //</editor-fold>

        //<editor-fold desc="Draw the bitching details">
        graphic.color = Color(0, 0, 0, 255)
//        ctx.channel.send(details ?: "no details").await()
        if (details?.isNotEmpty() == true) {
            details
//                .chunked(25)
                .split(" ")
                .dropLastWhile { it.isEmpty() }
                // Amount of "words" to chunk it into
                .chunked(10)
                // Amount of rows to collect
                .take(5)
                .iterator()
                .withIndex()
                .forEach {
                    // Calculate the height difference between rows based on the font height and groups of text
                    val heightDifference = graphic.fontMetrics.height * it.index
                    graphic.drawString(it.value.joinToString(" "), 100, 776 + (heightDifference - 2))
                }
        }
        //</editor-fold>

        graphic.dispose()

        val imageData = withContext(Dispatchers.IO) {
            val temp = ByteArrayOutputStream()
            ImageIO.write(bufferedImage, "png", temp)
            temp
        }


        interaction
            .sendMessage("Hey ${reporting.asMention}, ${ctx.interaction.member?.asMention} suggests you probably stop being a little b-")
            .addFiles(FileUpload.fromData(imageData.toByteArray(), "image.png"))
            .await()
    }
}
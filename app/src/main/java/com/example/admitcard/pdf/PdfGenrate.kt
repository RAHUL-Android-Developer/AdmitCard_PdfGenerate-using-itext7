package com.example.admitcard.Pdf

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getString
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.admitcard.DataItem
import com.example.admitcard.R
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.Color
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfName.BaseFont
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.List
import com.itextpdf.layout.element.ListItem
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Text
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.ListNumberingType
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PdfGenerate(private val context: Context) {
    val fontop = "res/font/opensans_semibold.ttf"
    val Hindifont = "res/font/h3.ttf"
    val light = "res/font/opensansregular.ttf"

    // Font




    @SuppressLint("SuspiciousIndentation", "UseCompatLoadingForDrawables")
    fun createPdf(responseDataList: DataItem, studentImage: Image, signatureImage: Image) {

        val pdfPath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "AdmitCard_$timestamp.pdf"
        val file = File(pdfPath, fileName)
        try {
            // Font

            val HindiCutom = PdfFontFactory.createFont(Hindifont, true)
            val Bold = PdfFontFactory.createFont(fontop, true)
            val lightfont = PdfFontFactory.createFont(light, true)

            val outputStream = FileOutputStream(file)
            val writer = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(writer)
            val document1 = Document(pdfDocument, PageSize.A4, true)
            document1.setMargins(85f, 50f, 35f, 45f)


            // Load and add left image to t
            val leftDrawable = context.getDrawable(R.drawable.img)
            val leftBitmap = (leftDrawable as BitmapDrawable).bitmap
            val leftStream = ByteArrayOutputStream()
            leftBitmap.compress(Bitmap.CompressFormat.PNG, 100, leftStream)
            val leftBitmapData = leftStream.toByteArray()
            val leftImageData = ImageDataFactory.create(leftBitmapData)
            val leftImage = Image(leftImageData)
            leftImage.setHeight(54f)
            leftImage.setWidth(54f)
            leftImage.setMarginTop(10f)
            leftStream.close()

            // Load and add right image to the table
            val rightDrawable = context.getDrawable(R.drawable.qr)
            val rightBitmap = (rightDrawable as BitmapDrawable).bitmap
            val rightStream = ByteArrayOutputStream()
            rightBitmap.compress(Bitmap.CompressFormat.PNG, 100, rightStream)
            val rightBitmapData = rightStream.toByteArray()
            val rightImageData = ImageDataFactory.create(rightBitmapData)
            val rightImage = Image(rightImageData)
            rightImage.setHeight(54f)
            rightImage.setWidth(54f)
            rightImage.setMarginTop(5f)
            rightImage.setMarginRight(15f)
            rightStream.close()

//            val hindiText = "पं. रविशंकर शुक्ल विश्वविद्यालय ,रायपुर(छ. ग.)"
            val hindiText = "ia- jfo'kadj 'kqDy fo'ofo|ky; ]jk;iqj¼N- x-½"
            val englishText = "PT. RAVISHANKAR SHUKLA UNIVERSITY, RAIPUR (C.G.)"
            val entranceText = "(Entrance 2023)"
            val admitCardText = "(M.P.Ed.) - Admit Card - 2023"
            Log.e("hereMe","its ok")


            val englishParagraph =
                Paragraph(englishText).setFont(Bold).setPadding(-2f).setMarginTop(5f)

            val hindiParagraph =
                Paragraph(hindiText).setPadding(-3f).setFont(HindiCutom).setMarginTop(4f)
                    .setFontSize(11.5f).setWordSpacing(-4.5f)
            val entranceParagraph =
                Paragraph(entranceText).setUnderline().setFont(Bold).setPadding(1f)
            val Admitcard = Paragraph(admitCardText).setFont(Bold).setPadding(-1f)

            val table = Table(UnitValue.createPercentArray(floatArrayOf(25f, 150f, 25f)))
            table.setMarginTop(2f).setMarginRight(10f)


            val leftCell =
                Cell().add(leftImage).setTextAlignment(TextAlignment.CENTER).setMarginTop(10f)
                    .setBorder(null)
            table.addCell(leftCell)

            val rightCell = Cell()
                .add(englishParagraph)
                .add(hindiParagraph)
                .add(entranceParagraph)
                .add(Admitcard)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(9.5f)
                .setBorder(null)
            table.addCell(rightCell)

            val midCell =
                Cell().add(rightImage).setTextAlignment(TextAlignment.CENTER).setMarginTop(5f)
                    .setBorder(null).setPadding(10f)
            table.addCell(midCell)

            val whiteb: Color = DeviceRgb(30, 30, 30)
            val border1 = SolidBorder(whiteb, 0.5f)
            // Add horizontal line below the table

            val solidLineSeparator = Paragraph()
                .setBorder(border1)
                .setFontColor(whiteb)
                .setMarginTop(2f)
                .setMarginBottom(5f)


            val rollNo: String = responseDataList.rollNo ?: "N/A"
            val formNo: String = responseDataList.formNo?.toString() ?: "N/A"
            val sname: String = responseDataList.sname ?: "N/A"
            val fname: String = responseDataList.fname ?: "N/A"
            val examDate: String = responseDataList.examDate ?: "N/A"
            val examTime: String = responseDataList.examTime ?: "N/A"
            val gender: String = responseDataList.gender ?: "N/A"
            val category: String = responseDataList.category ?: "N/A"
            val examCenter: String = responseDataList.examCenter ?: "N/A"
            val studnt_images: String = responseDataList.profileImage ?: "N/A"
            val studnt_signature: String = responseDataList.signatureImage ?: "N/A"


            //info
            val outerTable = Table(UnitValue.createPercentArray(floatArrayOf(500f, 265f)))
            outerTable.setBorder(Border.NO_BORDER).setMarginTop(5f)


            val innerLeftTable = Table(UnitValue.createPercentArray(floatArrayOf(130f, 250f)))
            innerLeftTable.addCell(siganlCellsBold("Roll Number"))
            innerLeftTable.addCell(siganlCellsBold(": $rollNo"))

            innerLeftTable.addCell(siganlCellsBold("Form Number"))
            innerLeftTable.addCell(siganlCells(": $formNo"))

            innerLeftTable.addCell(siganlCellsBold("Name of Candidate"))
            innerLeftTable.addCell(siganlCellsBold(": $sname"))

            innerLeftTable.addCell(siganlCellsBold("Father's Name "))
            innerLeftTable.addCell(siganlCells(": $fname"))

            innerLeftTable.addCell(siganlCellsBold("Date & Day of Exam"))
            innerLeftTable.addCell(siganlCells(": $examDate"))

            innerLeftTable.addCell(siganlCellsBold("Exam Time"))
            innerLeftTable.addCell(siganlCells(": $examTime"))

            innerLeftTable.addCell(siganlCellsBold("Gender"))
            innerLeftTable.addCell(siganlCells(": $gender"))

            innerLeftTable.addCell(siganlCellsBold("Category"))
            innerLeftTable.addCell(siganlCells(": $category"))

            innerLeftTable.addCell(siganlCellsBold("Exam Centre"))
            innerLeftTable.addCell(siganlCells(": $examCenter"))


            val innerRightTable = Table(UnitValue.createPercentArray(floatArrayOf(250f)))


            val borderColor: Color = DeviceRgb(0, 0, 0)
            val border = SolidBorder(borderColor, 1f)


            //StudentProfileImage
            innerRightTable.addCell(
                Cell().add(studentImage).setPadding(-0.3f).setBorder(border)
            )
            //Student_SignatureImage
            innerRightTable.addCell(
                Cell().add(signatureImage).setPadding(-0.3f).setBorder(border)
                    .setBorderTop(border)
            )
            innerRightTable.setHorizontalAlignment(HorizontalAlignment.RIGHT).setMarginTop(4f)
                .setMarginRight(20f)



            outerTable.addCell(Cell().add(innerLeftTable).setBorder(null))
            outerTable.addCell(Cell().add(innerRightTable).setBorder(null))
            // outerTable.addCell(Cell().add(innerRightTable).add(rightImage1Table))


            val sigCenterSuperintendent =
                Table(UnitValue.createPercentArray(floatArrayOf(250f)))
            val s1 = "Centre Superintendent"
            val s2 = "Pt. Ravishankar Shukla University Raipur"
            val para = Paragraph(s1).setFontSize(8.5f).setMultipliedLeading(0.80f)
            val para1 = Paragraph(s2).setFontSize(8.5f)
            val sigCenter = Cell()
                .add(para)
                .add(para1)
                .setTextAlignment(TextAlignment.RIGHT)
                .setFont(Bold)
                .setPadding(10f)
                .setBorder(null)

            Log.e("check_stream", "vg")


            sigCenterSuperintendent.addCell(sigCenter)
                .setMarginTop(13f)
                .setMarginRight(13f)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)


            //English Instructions
            val Inst = "Instructions for Candidates"
            val Instruct_ = Paragraph(Inst)
                .setFontSize(9f)
                .setUnderline()
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(Bold)
                .setMarginTop(22f)
                .setFontSize(11f)

            val orderedList: List = List()
                .setFont(PdfFontFactory.createFont())
                .setFontSize(8f)
                .setTextAlignment(TextAlignment.LEFT)
                .setListSymbol(ListNumberingType.DECIMAL)
                .setMarginTop(15f)
                .setFont(lightfont)
                .setMarginRight(50f)

            //line _2

            val paragraphText = getString(context, R.string.line_2)
            val paragraph = Paragraph(paragraphText).setFont(Bold)
            val listItem = ListItem()
            paragraph.setFixedLeading(10.5f)
            listItem.add(paragraph)

            //line 6
            val paragraphText_6 = getString(context, R.string.line_6)
            val paragraph_6 = Paragraph(paragraphText_6).setFont(lightfont)
            val listItem_6 = ListItem()
            paragraph_6.setFixedLeading(10.5f)
            listItem_6.add(paragraph_6)

            //line 8
            val paragraphText_8 = getString(context, R.string.line_8)
            val paragraph_8 = Paragraph(paragraphText_8).setFont(lightfont)
            val listItem_8 = ListItem()
            paragraph_8.setFixedLeading(10.5f)
            listItem_8.add(paragraph_8)

            //line 9
            val paragraphText_9 = getString(context, R.string.line_9)
            val paragraph_9 = Paragraph(paragraphText_9).setFont(lightfont)
            val listItem_9 = ListItem()
            paragraph_9.setFixedLeading(10.5f)
            listItem_9.add(paragraph_9)

//              line 11
            val markingSchemeText = getString(context, R.string.line_11)
            val paragraph_1 = Paragraph()
            val prefix = "Marking Scheme:"
            val prefixText = Text(prefix).setFont(Bold)
            paragraph_1.add(prefixText)
            val normalPart = markingSchemeText.substringAfter(prefix)
            val normalText = Text(normalPart)
            paragraph_1.add(normalText)
            val listItem_11 = ListItem()
            paragraph_1.setFixedLeading(10.5f)
            listItem_11.add(paragraph_1)
            listItem_11.setKeepTogether(true)

            //line 12
            val markingSchemeText12 = getString(context, R.string.line_12)
            val paragraph_12 = Paragraph()
            val prefix12 = "Language of the question paper:"
            val prefixText12 = Text(prefix12).setFont(Bold)
            paragraph_12.add(prefixText12)
            val normalPart12 = markingSchemeText12.substringAfter(prefix12)
            val normalText12 = Text(normalPart12)
            paragraph_12.add(normalText12)
            paragraph_12.setFixedLeading(10.5f)
            val listItem_12 = ListItem()
            listItem_12.add(paragraph_12)
            listItem_12.setKeepTogether(true)

            // line 13
            val paragraphText_13 = getString(context, R.string.line_13)
            val paragraph_13 = Paragraph(paragraphText_13).setFont(lightfont)
            val listItem_13 = ListItem()
            paragraph_13.setFixedLeading(10.5f)
            listItem_13.add(paragraph_13)

            //2,6,8,9,11,12,13
            orderedList.add(ListItem(getString(context, R.string.line_1)))
            orderedList.add(listItem)
            orderedList.add(ListItem(getString(context, R.string.line_3)))
            orderedList.add(ListItem(getString(context, R.string.line_4)))
            orderedList.add(ListItem(getString(context, R.string.line_5)))
            orderedList.add(listItem_6)
            orderedList.add(ListItem(getString(context, R.string.line_7)))
            orderedList.add(listItem_8)
            orderedList.add(listItem_9)
            orderedList.add(ListItem(getString(context, R.string.line_10)))
            orderedList.add(listItem_11)
            orderedList.add(listItem_12)
            orderedList.add(listItem_13)
            orderedList.add(ListItem(getString(context, R.string.line_14)))
            orderedList.add(ListItem(getString(context, R.string.line_15)))


            //Hindi Instructions
            val InstHindi = "vH;fFkZ;ksa ds fy, funsZ'k"
            val InstructHindi_ = Paragraph(InstHindi)
                .setUnderline()
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(HindiCutom)
                .setPageNumber(2)
                .setFontSize(14f)


            val HindiInstructionImage = Table(UnitValue.createPercentArray(floatArrayOf(250f)))



            Log.e("check_stream", "vg")
            document1.add(table)
            document1.add(solidLineSeparator)
            document1.add(outerTable)
            document1.add(sigCenterSuperintendent)
            document1.add(Instruct_)
            document1.add(orderedList)
            document1.add(InstructHindi_)
            document1.add(HindiInstructionImage)
            Log.e("check_stream", "vg")
            Toast.makeText(context, "PDF created and downloaded", Toast.LENGTH_SHORT).show()
            openPdfFile(file)



            document1.close()
            writer.close()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show()
        }

    }

    private fun openPdfFile(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        val pdfUri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        )
        intent.setDataAndType(pdfUri, "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }

    private fun siganlCells(value: String): Cell {
        val cell = Cell()
        val paragraph = Paragraph()
        val lightfont = PdfFontFactory.createFont(light, true)
        paragraph.add(value)
        cell.add(paragraph).setFontSize(9f)
        cell.setPadding(-2f)
        cell.setBorder(null)
        cell.setFont(lightfont)
        return cell
    }

    private fun siganlCellsBold(value: String): Cell {
        val cell = Cell()
        val paragraph = Paragraph()
        val Bold = PdfFontFactory.createFont(fontop, true)
        paragraph.add(value)
        cell.add(paragraph).setFontSize(9f)
        cell.setPadding(-2f)
        cell.setBorder(null)
        cell.setFont(Bold)

        return cell
    }




}







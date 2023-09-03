package com.futuristicmobileapps.tools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.futuristicmobileapps.designsystem.R
import com.futuristicmobileapps.designsystem.component.ToolsCard
import com.futuristicmobileapps.designsystem.theme.PdfToolsIcons
import com.futuristicmobileapps.tools.util.Action
import com.futuristicmobileapps.tools.util.PdfTool
import kotlin.math.roundToInt

fun getBasicToolsList() = mutableListOf(
    PdfTool(action = Action.SPLIT, label = R.string.split_pdf, icon = R.drawable.split_icon),
    PdfTool(action = Action.MERGE, label = R.string.merge_pdf, icon =R.drawable.merge_icon),
    PdfTool(action = Action.ADD_PAGE_NUMBER, label = R.string.add_page_number, icon = R.drawable.add_page_number_icon),
    PdfTool(action = Action.REORDER, label = R.string.reorder_pages, icon = R.drawable.re_order_icon),
    PdfTool(action = Action.REMOVE_PAGE, label = R.string.remove_pages, icon = R.drawable.remove_page_icon),
    PdfTool(action = Action.ROTATE, label = R.string.rotate_pages, icon = R.drawable.rotate_icon),
    PdfTool(action = Action.IMAGES_TO_PDF, label = R.string.images_to_pdf, icon = R.drawable.images_pdf_icon),
    PdfTool(action = Action.PDF_TO_IMAGES, label = R.string.extract_images, icon = R.drawable.pdf_images_icon),
    PdfTool(action = Action.ADD_WATERMARK, label = R.string.add_watermark, icon =R.drawable.add_watermark_icon),
    PdfTool(action = Action.INVERT, label = R.string.invert_pdf, icon = R.drawable.invert_pdf_icon),
    PdfTool(action = Action.REMOVE_DUPLICATE, label = R.string.remove_duplicate, icon = R.drawable.reomve_duplicate_icon),
)

fun getSecureToolsList() = mutableListOf(
    PdfTool(action = Action.ADD_PASSWORD, label = R.string.secure_pdf, icon = R.drawable.lock_pdf_icon),
    PdfTool(action = Action.REMOVE_PASSWORD, label = R.string.remove_password, icon = R.drawable.unlock_pdf_icon),
)

fun getOtherToolsList(pdfToolsIcons: PdfToolsIcons) = mutableListOf(
    PdfTool(action = Action.DOC_TO_PDF, label = R.string.doc_to_pdf, icon = pdfToolsIcons.merge),
)




@Composable
fun ToolsAdaptiveLayout(
    screenWidth: Dp,
    toolsList: MutableList<PdfTool>,
    modifier: Modifier,
    onCardClick: (pdfTool: PdfTool) -> Unit
) {

    val screenWidthAfterPadding =
        if (screenWidth < 450.dp) (screenWidth - 60.dp) else screenWidth - (screenWidth * 0.05f)

    val numOfItemsPerRow =
        if (screenWidth < 450.dp) 3 else (screenWidthAfterPadding / 130.dp).roundToInt()

    val numberOfItems = toolsList.size

    var numberOfColumns = (numberOfItems / numOfItemsPerRow)

    val remainder = numberOfItems % numOfItemsPerRow

    if (remainder > 1)
        numberOfColumns += 1

    Column(modifier = Modifier.padding(start = 2.dp)) {

        for (column in 0..numberOfColumns) {

            Row {

                for (toolIndex in column * numOfItemsPerRow until ((column + 1) * numOfItemsPerRow)) {

                    if (toolIndex < toolsList.size) {

                        val pdfTool = toolsList[toolIndex]

                        ToolsCard(
                            modifier = modifier,
                            imageDrawable = pdfTool.icon,
                            toolText = pdfTool.label
                        ) {
                            onCardClick(toolsList[toolIndex])
                        }
                    }
                }

            }

        }
    }

}
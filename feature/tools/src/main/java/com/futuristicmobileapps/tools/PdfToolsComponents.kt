package com.futuristicmobileapps.tools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

fun getBasicToolsList(pdfToolsIcons: PdfToolsIcons) = mutableListOf(
    PdfTool(action = Action.SPLIT, label = R.string.split_pdf, icon = pdfToolsIcons.merge),
    PdfTool(action = Action.MERGE, label = R.string.merge_pdf, icon = pdfToolsIcons.merge),
    PdfTool(action = Action.ROTATE, label = R.string.rotate_pdf, icon = pdfToolsIcons.merge),
    PdfTool(action = Action.REORDER, label = R.string.reorder_pages, icon = pdfToolsIcons.merge),
    PdfTool(action = Action.REMOVE_DUPLICATE, label = R.string.remove_duplicate, icon = pdfToolsIcons.merge),
    PdfTool(action = Action.INVERT, label = R.string.invert_pdf, icon = pdfToolsIcons.merge),
    PdfTool(action = Action.ADD_PAGE_NUMBER, label = R.string.add_page_number, icon = pdfToolsIcons.merge),
    PdfTool(action = Action.IMAGES_TO_PDF, label = R.string.images_to_pdf, icon = pdfToolsIcons.merge)
)

fun getSecureToolsList(pdfToolsIcons: PdfToolsIcons) = mutableListOf(
    PdfTool(action = Action.ADD_PASSWORD, label = R.string.secure_pdf, icon = pdfToolsIcons.merge),
    PdfTool(action = Action.REMOVE_PASSWORD, label = R.string.remove_password, icon = pdfToolsIcons.merge),
)

fun getOtherToolsList(pdfToolsIcons: PdfToolsIcons) = mutableListOf(
    PdfTool(action = Action.ADD_WATERMARK, label = R.string.add_watermark, icon = pdfToolsIcons.merge),
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

    Column {

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
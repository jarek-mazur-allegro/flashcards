package org.example;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PdfCreator {
    public void pdfCreator (List<Map<String, String>> flashCardsList) {
        try (PDDocument document = new PDDocument()) {
            int flashCardsPerPage = 20;
            double numberOfPages = Math.ceil((double) flashCardsList.size() / flashCardsPerPage);

            for (int currentPage = 0; currentPage < numberOfPages; currentPage++) {
                PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth())); // Orientacja pozioma
                document.addPage(page);

                final int rows = 4;
                final int cols = 5;
                final float margin = 30;
                final float rowHeight = (page.getMediaBox().getHeight() - 2 * margin) / rows;
                final float colWidth = (page.getMediaBox().getWidth() - 2 * margin) / cols;

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                    for (int r = 0; r < rows; r++) {
                        for (int c = 0; c < cols; c++) {
                            int currentFlashCardIndex = currentPage * flashCardsPerPage + c+1 +r*cols;
                            System.out.println(currentFlashCardIndex);
                            if (currentFlashCardIndex > flashCardsList.size()) {
                                break;
                            }
                            float x = margin + c * colWidth;
                            float y = page.getMediaBox().getHeight() - margin - (r + 1) * rowHeight;

                            // Rysowanie prostokata (opcjonalne)
                            contentStream.addRect(x, y, colWidth, rowHeight);
                            contentStream.stroke();
                            int fontSize = 8;

                            // Dodanie tekstu na górze prostokata
                            String topText = flashCardsList.get(currentFlashCardIndex-1).get("eng");
                            float topStringWidth = PDType1Font.TIMES_ROMAN.getStringWidth(topText) / 1000 * fontSize;
                            float startXTop = (colWidth - topStringWidth) / 2 + x + 5;

                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
                            contentStream.newLineAtOffset(startXTop, y + rowHeight - 15);
                            contentStream.showText(topText);
                            contentStream.endText();

                            // Dodanie tekstu na dole prostokata
                            String bottomText = flashCardsList.get(currentFlashCardIndex-1).get("pl");
                            float bottomStringWidth = PDType1Font.TIMES_ROMAN.getStringWidth(bottomText) / 1000 * fontSize;
                            float startXBottom = (colWidth - bottomStringWidth) / 2 + x + 5;

                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
                            contentStream.newLineAtOffset(startXBottom, y + 5);
                            contentStream.showText(bottomText);
                            contentStream.endText();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            System.out.println("here");
            document.save("vol 3.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package br.com.senai.util;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.senai.tela.TelaCaixa;

public class GerarPDF {

	public void GerarPdf() throws Exception {

        Document doc = null;
        OutputStream os = null;

        try {

            //cria o documento tamanho A4, margens de 2,54cm
            doc = new Document(PageSize.A4, 72, 72, 72, 72);

            //cria a stream de saída

            //associa a stream de saída ao
            PdfWriter.getInstance(doc, new FileOutputStream("comprovante-"+TelaCaixa.l.getId()+".pdf"));

            //abre o documento
            doc.open();
            
            Font fontmaior = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
            Font fontmedia = new Font(Font.FontFamily.HELVETICA, 12);
            Font fontmenor = new Font(Font.FontFamily.HELVETICA, 10);

			Image img = Image.getInstance("menor_logo2.png");
			img.setAlignment(Element.ALIGN_CENTER);
			doc.add(img);
		
            Paragraph pId = new Paragraph("Cód. Locação: " + TelaCaixa.l.getId(), fontmaior);
            pId.setSpacingBefore(10);
            pId.setSpacingAfter(5);
            pId.setAlignment(Element.ALIGN_CENTER);
			doc.add(pId);
			
			Paragraph pClienteNome = new Paragraph("Nome: " + TelaCaixa.l.getCliente().getNome(), fontmedia);
			Paragraph pCliente = new Paragraph("RG: " + TelaCaixa.l.getCliente().getRg() + " CPF: " + TelaCaixa.l.getCliente().getCpf(), fontmedia);
			Paragraph pCliente2 = new Paragraph("Email: " + TelaCaixa.l.getCliente().getEmail() + " Telefone: " + TelaCaixa.l.getCliente().getTelefone(), fontmedia);
			pClienteNome.setSpacingBefore(20);
			pCliente2.setSpacingAfter(20);
			doc.add(pClienteNome);
			doc.add(pCliente);
			doc.add(pCliente2);
			
			Paragraph pLocal = new Paragraph("Local: " + TelaCaixa.l.getLocal_Reserva(), fontmenor);
			pLocal.setSpacingAfter(10);
			doc.add(pLocal);		
			
			Paragraph pRetirada = new Paragraph("Data Retirada: " + TelaCaixa.l.getData_Retirada() + " Hora: " + TelaCaixa.l.getHora_Retirada(), fontmenor);
			pRetirada.setSpacingBefore(10);
			pRetirada.setSpacingAfter(5);
			doc.add(pRetirada);
			
			Paragraph pPrevista = new Paragraph("Data Prevista: " + TelaCaixa.l.getData_Prevista() + " Hora: " + TelaCaixa.l.getHora_Prevista(), fontmenor);
			pPrevista.setSpacingAfter(5);
			doc.add(pPrevista);
			
			Paragraph pGrupo = new Paragraph("Grupo: " +TelaCaixa.l.getGrupo().getNome()+ " - " +TelaCaixa.l.getGrupo().getDescricao() + " Preço: " + TelaCaixa.l.getGrupo().getPreco(), fontmenor);
			pGrupo.setSpacingBefore(10);
			pGrupo.setSpacingAfter(5);
			doc.add(pGrupo);
			
			Paragraph pProteção = new Paragraph("Proteção: " +TelaCaixa.l.getProtecao().getDescricao()+ " Preço: " + TelaCaixa.l.getProtecao().getPreco(), fontmenor);
			pProteção.setSpacingBefore(10);
			pProteção.setSpacingAfter(5);
			doc.add(pProteção);
			
			Paragraph pExemplar = new Paragraph("Exemplar: " +TelaCaixa.l.getExemplar().getPlaca(), fontmenor);
			pExemplar.setSpacingBefore(10);
			pExemplar.setSpacingAfter(5);
			doc.add(pExemplar);
			
			Paragraph total = new Paragraph("Total: " +TelaCaixa.tfTotal.getText(), fontmaior);
			total.setSpacingBefore(30);
			doc.add(total);
			
        } catch (DocumentException de) {
			de.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			doc.close();
        }
    }
    
}

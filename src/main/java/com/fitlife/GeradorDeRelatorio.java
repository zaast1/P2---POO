package com.fitlife;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.fitlife.Aluno.Aluno;
import com.fitlife.Professor.Professor;
import com.fitlife.Modalidade.Modalidade;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeradorDeRelatorio {

    // O PDF
    // Se o iText falhar, a culpa é da dependência, não nossa.
    public void gerarRelatorioCompleto(List<Aluno> alunos, List<Professor> professores, List<Modalidade> modalidades, String caminho) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminho));
            document.open();

            // Fontes para ficar chique
            Font tituloF = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font subTituloF = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY);
            Font textoF = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            // Cabeçalho
            document.add(new Paragraph("RELATÓRIO GERAL - FITLIFE ACADEMIA", tituloF));
            document.add(new Paragraph("Gerado em: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), textoF));
            document.add(new Paragraph("-----------------------------------------------------------------"));
            document.add(new Paragraph(" "));

            // 1. ALUNOS - Quem paga as contas
            document.add(new Paragraph("1. LISTA DE ALUNOS", subTituloF));
            if (alunos.isEmpty()) document.add(new Paragraph("Nenhum aluno. Estamos falindo?", textoF));
            for (Aluno a : alunos) {
                String plano = (a.getPlano() != null) ? a.getPlano().getNome() : "Sem Plano";
                String vipTag = plano.toUpperCase().contains("VIP") ? " [VIP] 🌟" : "";
                document.add(new Paragraph(String.format("ID: %d | %s | %s%s", a.getId(), a.getNome(), plano, vipTag), textoF));
            }
            document.add(new Paragraph(" "));

            // 2. PROFESSORES - Quem grita com os alunos
            document.add(new Paragraph("2. EQUIPE DE PROFESSORES", subTituloF));
            if (professores.isEmpty()) document.add(new Paragraph("Nenhum professor. Aula autodidata?", textoF));
            for (Professor p : professores) {
                document.add(new Paragraph(String.format("ID: %d | %s", p.getId(), p.getNome()), textoF));
            }
            document.add(new Paragraph(" "));

            // 3. MODALIDADES - O que tem pra fazer
            document.add(new Paragraph("3. MODALIDADES OFERECIDAS", subTituloF));
            if (modalidades.isEmpty()) document.add(new Paragraph("Nenhuma modalidade.", textoF));
            for (Modalidade m : modalidades) {
                document.add(new Paragraph(String.format("ID: %d | %s", m.getId(), m.getNome()), textoF));
            }

            System.out.println("📄 Relatório PDF Completo gerado em: " + caminho);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
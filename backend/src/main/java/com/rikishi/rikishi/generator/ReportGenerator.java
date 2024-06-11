package com.rikishi.rikishi.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.provider.ConfigProvider;
import com.rikishi.rikishi.provider.EmojiFlagProvider;
import com.rikishi.rikishi.service.FightService;
import com.rikishi.rikishi.service.UserService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class ReportGenerator {
    private static final String DEFAULT_FONT_NAME = FontFactory.TIMES;
    private static final BaseColor WIN_COLOR = BaseColor.GREEN;
    private static final BaseColor LOOSE_COLOR = BaseColor.RED;
    private static final BaseColor UNKNOWN_COLOR = BaseColor.LIGHT_GRAY;

    private final UserService userService;
    private final FightService fightService;
    private final ConfigProvider configProvider;
    private final EmojiFlagProvider emojiFlagProvider;

    private final Document document;

    private final Font titleFont;
    private final Font subtitleFont;
    private final Font h1Font;
    private final Font h2Font;
    private final Font normalFont;
    private final Font normalBoldFont;

    public ReportGenerator(
        UserService userService,
        FightService fightService,
        ConfigProvider configProvider,
        EmojiFlagProvider emojiFlagProvider
    ) {
        this.userService = userService;
        this.fightService = fightService;
        this.configProvider = configProvider;
        this.emojiFlagProvider = emojiFlagProvider;

        document = new Document();

        titleFont = FontFactory.getFont(DEFAULT_FONT_NAME, 18, Font.BOLD);
        subtitleFont = FontFactory.getFont(DEFAULT_FONT_NAME, 12, Font.ITALIC);
        h1Font = FontFactory.getFont(DEFAULT_FONT_NAME, 16, Font.BOLD);
        h2Font = FontFactory.getFont(DEFAULT_FONT_NAME, 14, Font.BOLD);
        normalFont = FontFactory.getFont(DEFAULT_FONT_NAME, 12, Font.NORMAL);
        normalBoldFont = FontFactory.getFont(DEFAULT_FONT_NAME, 12, Font.BOLD);
    }

    public void generate(String saveTo) throws DocumentException, FileNotFoundException {
        var date = new Date(System.currentTimeMillis());
        var title = String.format("Sumo report: %s", date);

        PdfWriter.getInstance(document, new FileOutputStream(saveTo));

        document.addTitle(title);
        document.open();

        addTitle("Sumo Report");
        addSubTitle(date.toString());

        addContestantsSection();
        document.add(new Chunk("\n"));
        addFightsSection();

        document.close();
    }

    private void addFightsSection() throws DocumentException {
        addH1("Fights");

        configProvider.getWeightClasses().forEach(weightClass -> {
            try {
                addFightWeightClassSection(weightClass);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addFightWeightClassSection(WeightClass weightClass) throws DocumentException {
        if (fightService.getAllCategoryFights(weightClass).findAny().isEmpty())
            return;

        addH2(weightClass.name());

        // fighter 1 (name + surname), fighter 2, number, score (1:2)
        var table = new PdfPTable(4);

        addTableHeader(table, List.of("Fighter 1", "Fighter 2", "Number", "Score"));

        fightService.getAllCategoryFights(weightClass).forEach(fight -> {
            Function<User, BaseColor> fighterColor = fighter -> {
                if (fight.winnerId() == -1)
                    return UNKNOWN_COLOR;
                return fighter.id() == fight.winnerId() ? WIN_COLOR : LOOSE_COLOR;
            };

            var f1 = fight.firstUser();
            var f2 = fight.secondUser();

            addTableColumns(table, List.of(
                f1 != null ? new Column(String.format("%s %s", f1.name(), f1.surname()), fighterColor.apply(f1)) : text(""),
                f2 != null ? new Column(String.format("%s %s", f2.name(), f2.surname()), fighterColor.apply(f2)) : text(""),
                text(String.valueOf(fight.number())),
                text(String.format("%s:%s", fight.score1(), fight.score2()))
            ));
        });

        document.add(table);
    }

    private void addContestantsSection() throws DocumentException {
        addH1("Contestants");

        // id, name, surname, age, weight, weightClass, sex, country, place
        var table = new PdfPTable(9);

        addTableHeader(table, List.of(
            "Id", "Name", "Surname", "Age", "Weight", "Weight Class", "Sex", "Country", "Place"
        ));

        userService.getUsers().forEach(user -> {
            var country = user.country();

            addTableColumns(table, List.of(
                text(String.valueOf(user.id())),
                text(user.name()),
                text(user.surname()),
                text(String.valueOf(user.age())),
                text(String.valueOf(user.weight())),
                text(user.weightClass().name()),
                text(user.sex().name()),
                text(emojiFlagProvider.flagOf(country) + country), // TODO: render emojis correctly
                text("N/A") // TODO
            ));
        });

        document.add(table);
    }

    private void addTableColumns(PdfPTable table, Iterable<Column> columns) {
        columns.forEach(col -> {
            var cell = new PdfPCell(new Phrase(col.text, normalFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);

            if (col.backgroundColor != null)
                cell.setBackgroundColor(col.backgroundColor);

            table.addCell(cell);
        });
    }

    private void addTableHeader(PdfPTable table, Iterable<String> columns) {
        columns.forEach(columnTitle -> {
            var header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setPhrase(new Phrase(columnTitle, normalBoldFont));
            table.addCell(header);
        });
    }

    private void addTitle(String title) throws DocumentException {
        var para = new Paragraph(title, titleFont);
        para.setAlignment(Element.ALIGN_CENTER);
        para.setSpacingAfter(8);
        document.add(para);
    }

    private void addSubTitle(String subtitle) throws DocumentException {
        var para = new Paragraph(subtitle, subtitleFont);
        para.setAlignment(Element.ALIGN_CENTER);
        para.setSpacingAfter(16);
        document.add(para);
    }

    private void addH1(String header) throws DocumentException {
        var chunk = new Chunk(header + "\n", h1Font);
        document.add(chunk);
    }

    private void addH2(String header) throws DocumentException {
        var chunk = new Chunk(header + "\n", h2Font);
        document.add(chunk);
    }

    private static Column text(String s) {
        return new Column(s, null);
    }

    private record Column(
        String text,
        BaseColor backgroundColor
    ) {}
}

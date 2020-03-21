package Domain;

import java.time.LocalDate;

public class StructuraAn {
    private LocalDate start;
    private LocalDate end;
    private LocalDate start_Break;
    private LocalDate end_Break;

    public StructuraAn(LocalDate start, LocalDate end, LocalDate start_Break, LocalDate end_Break) {
        this.start = start;
        this.end = end;
        this.start_Break = start_Break;
        this.end_Break = end_Break;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public LocalDate getStartBreak() {
        return start_Break;
    }

    public LocalDate getEndBreak() {
        return end_Break;
    }

}

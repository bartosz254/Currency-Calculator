package pl.parser.nbp;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.lang.String;

public class UserInterface {

    public void start(String[] args) {

        if (args.length != 3) {
            throw new RuntimeException("NOT ENOUGH ARGUMENTS");
        }

        CurrencyType currencyType = parseCurrency(args[0]);
        LocalDate startDate = parseStartDate(args[1]);
        LocalDate endDate = parseEndDate(args[2]);

        new Currency(currencyType,startDate,endDate);

    }
    private LocalDate parseStartDate(String arg) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate startDate = LocalDate.parse(arg, formatter);
        return startDate;
    }

    private LocalDate parseEndDate(String arg) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate endDate = LocalDate.parse(arg, formatter);
        return endDate;
    }

    private CurrencyType parseCurrency(String arg) {
        try {
            return CurrencyType.valueOf(arg);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(new IllegalAccessException("NO CURRENCY SPECIFIED"));
        }
    }
}

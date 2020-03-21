package Domain.Validator;

import Domain.Homework;

import java.util.function.Predicate;

public class HomeworkValidator implements Validator<Homework> {
    @Override
    public void validate(Homework entity) throws ValidatorException {
        if (entity == null)
            throw new ValidatorException("Homework cannot be NULL \n");

        String err = "";
        Predicate<Homework> negID = x -> x.getID() < 0;
        Predicate<Homework> invalidRecWeek = x -> x.getWeekRecv() < 1 && x.getWeekRecv() > 14;
        Predicate<Homework> invalidDeadline = x -> x.getWeekRecv() < 1 && x.getWeekRecv() > 14;
        Predicate<Homework> invalidTitle = x -> x.getTitle().equals("");
        Predicate<String> emptyErr = x -> x.equals("");

        if (negID.test(entity))
            err += "ID Must Not Be Negative";
        if (invalidDeadline.test(entity))
            err += "Invalid deadline";
        if (invalidRecWeek.test(entity))
            err += "Invalid start week";
        if (invalidTitle.test(entity))
            err += "Please add a description for the homework";
        if (!emptyErr.test(err))
            throw new ValidatorException(err);
    }
}

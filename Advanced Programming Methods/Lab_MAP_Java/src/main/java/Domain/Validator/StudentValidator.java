package Domain.Validator;
import Domain.Student;
import java.util.function.Predicate;

public class StudentValidator implements Validator<Student>{
    @Override
    public void validate(Student student) throws ValidatorException {
        if (student == null)
            throw new ValidatorException ("Student CAN'T be NULL");

        String err="";
        Predicate<Student> invalidID = x -> x.getID() < 0;
        Predicate<Student> emptyEmail = x -> x.getEmail().equals("");
        Predicate<Student> emptyName = x -> x.getName().equals("");
        Predicate<Student> invalidGroup = x -> x.getGroup() < 0;
        Predicate<String> emptyErrs = x -> x.equals("");

        if (invalidID.test(student)){
            err += "ID must be a positive number\n";
        }
        if (emptyEmail.test(student)){
            err += "Email cannot be empty\n";
        }
        if (emptyName.test(student)){
            err += "Student name cannot be empty\n";
        }

        if (invalidGroup.test(student)){
            err += "Group must be positive\n";
        }
        if (!emptyErrs.test(err)){
            throw new ValidatorException(err);
        }
    }
}

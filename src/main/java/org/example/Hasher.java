package org.example;
import org.mindrot.jbcrypt.BCrypt;

public class Hasher {
    static String hashPassword(String password) {
        // Генерируем соль и хэш-значение пароля
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    static boolean PasswordIsValid(String password, String passwordDB){

        if (BCrypt.checkpw(password, passwordDB)) {
            return true;
        } else {
            return false;
        }
    }
}

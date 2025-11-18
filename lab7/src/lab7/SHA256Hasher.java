package lab7;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SHA256Hasher {
    
    // Hash password with SHA-256
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        return bytesToHex(hashedBytes);
    }
    
    // Convert byte array to hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        if (bytes == null) return "";
        
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    // Check if password matches the hash
    public static boolean checkPassword(String password, String hashedPassword) throws NoSuchAlgorithmException {
        if (password == null || hashedPassword == null) {
            return false;
        }
        
        String newHash = hashPassword(password);
        return newHash.equals(hashedPassword);
    }
    
    // Utility method to validate email format
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
    
    // Utility method to validate password strength
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        // Basic strength check - at least 6 characters
        return password.length() >= 6;
    }
}
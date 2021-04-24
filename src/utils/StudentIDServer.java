package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class to generate ID
 *
 * @version 1.0
 */
public class StudentIDServer {
    /**
     * Generate id of 8 chars using MD5
     *
     * @param identity A string of a teams identity
     * @return A string of 8 chars
     */
    public static String getId( String identity ) {

        String id = "XXXXXXXX";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance( "MD5" );
            md.update( identity.getBytes() );
            byte[] digest = md.digest();
            //convertir le tableau de bits en une format hexadécimal
            StringBuffer sb = new StringBuffer();
            sb.append( Integer.toString( ( digest[0] & 0xff ) + 0x100, 16 ).substring( 1 ) );
            sb.append( Integer.toString( ( digest[1] & 0xff ) + 0x100, 16 ).substring( 1 ) );
            sb.append( Integer.toString( ( digest[2] & 0xff ) + 0x100, 16 ).substring( 1 ) );
            sb.append( Integer.toString( ( digest[3] & 0xff ) + 0x100, 16 ).substring( 1 ) );
            /*digest.length*/
            id = sb.toString();

        } catch( NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }

        return id;
    }

}

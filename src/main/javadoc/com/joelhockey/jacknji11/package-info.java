/**
 * jacknji11 provides 3 interfaces for calling cryptoki functions: {@link com.joelhockey.jacknji11.CE},
 * {@link com.joelhockey.jacknji11.C}, and {@link com.joelhockey.jacknji11.Native}.
 * <ol>
 * <li>{@link com.joelhockey.jacknji11.Native} provides the lowest level JNA direct mapping to the C_* functions.
 * There is little reason why you would ever want to invoke it directly, but you can.
 * <li>{@link com.joelhockey.jacknji11.C} provides the exact same interface as {@link com.joelhockey.jacknji11.Native} by
 * calling through to the corresponding native method.  The 'C_' at the start
 * of the function is removed since 'C.' when you call the static methods looks
 * equivalent.  In addition to {@link com.joelhockey.jacknji11.Native}, {@link com.joelhockey.jacknji11.C} handles some of the low-level
 * JNA plumbing such as 'pushing' any values changed within the native call back into
 * java objects.  You can use this if you require fine-grain control over something.
 * <li>{@link com.joelhockey.jacknji11.CE} provides the most user-friendly interface.  It calls the
 * {link com.joelhockey.jacknji11.C} equivalent function, and converts any non-zero return values into
 * a {@link com.joelhockey.jacknji11.CKRException}, and automatically resizes arrays and other helpful things.
 * I recommend that you use it exclusively if possible.
 * </ol>
 * <p>Example usage:
<pre>
        int TESTSLOT = 0;
        byte[] USER_PIN = "userpin".getBytes();
        int session = CE.OpenSession(TESTSLOT);
        CE.LoginUser(session, USER_PIN);

        int des3key = CE.GenerateKey(session, new CKM(CKM.DES3_KEY_GEN),
                new CKA(CKA.VALUE_LEN, 24),
                new CKA(CKA.LABEL, "label"),
                new CKA(CKA.SENSITIVE, false),
                new CKA(CKA.DERIVE, true));

        CE.EncryptInit(session, new CKM(CKM.DES3_CBC_PAD), des3key);
        byte[] plaintext = new byte[10];
        byte[] encrypted = CE.Encrypt(session, plaintext);

        CE.DecryptInit(session, new CKM(CKM.DES3_CBC_PAD), des3key);
        byte[] decrypted = CE.Decrypt(session, encrypted);
        assertTrue(Arrays.equals(plaintext, decrypted));
</code>
 */

package com.joelhockey.jacknji11;

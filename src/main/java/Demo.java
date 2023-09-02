public class Demo {
    public static void main(String[] args) {
        String s = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIzIiwic3ViIjoieHlodWFuZyIsImlhdCI6MTY5MTc4MDkyOCwiaXNzIjoiY29tLmRlbW8iLCJleHAiOjE2OTE4NjczMjh9.cwbPcQU4iBaIAUfFfhIz7JgY_Dgv2q6M6zjHRXJ3F2mw0ufCs_xMZ0XIy_5VSpZO1iCsP-vxpWm4XNp4tGX1qQ";
        String s2 = s.split("\\.")[1].trim();

        System.out.println(s2.length());
    }
}

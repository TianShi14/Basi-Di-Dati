import java.util.*;
import java.sql.*;

public class Main {
    public static class CustomExceptions extends Exception{
        public CustomExceptions(String m){
            super(m);
        }
    }
    public static void CheckExc(float val, Connection c) throws CustomExceptions, SQLException {
        Statement s_tmp = c.createStatement();
        ResultSet r_tmp = s_tmp.executeQuery("SELECT department FROM professor");

        if (val >= 1940.0 && val < 1941.0){
            throw new CustomExceptions("Dep randomizzato è pari a 1940");
        }

        s_tmp.close();
        r_tmp.close();
    }
    final static int N = 100;
    final static int BatchDim = 2000;

    public static Integer[] InitIntList(){
        Integer[] list = new Integer[N - 1];
        for (int i = 0; i < N; ++i){
            list[i] = i +1;
        }
        Collections.shuffle(Arrays.asList(list));
        return list;
    }
    public static Float[] InitFloatList(Integer[] l){
        Float[] list = new Float[l.length];
        for (int i = 0; i< l.length; ++i){
            list[i] = (float)l[i] + randFloat(1);
        }
        Collections.shuffle(Arrays.asList(list));
        return list;
    }

    public static int randInt(int bound){
        Random tmp = new Random();
        return tmp.nextInt(bound);
    }
    public static float randFloat(float bound){
        Random tmp = new Random();
        return tmp.nextFloat(bound);
    }
    public static String randString(int dim){
        int lLimit = 97; // lettera 'a'
        int rLimit = 122; // lettera 'z'
        Random rand = new Random();
        StringBuilder buff = new StringBuilder(dim);
        for (int i = 0; i < dim; i++) {
            int randomLimitedInt = lLimit + (int)
                    (rand.nextFloat() * (rLimit - lLimit + 1));
            buff.append((char) randomLimitedInt);
        }
        return buff.toString();
    }

    public static void printQuery(ResultSet r) throws SQLException{
        int i = 0;
        while (r.next()){
            i = i + 1;
            System.err.print(i + ": " + "| ID: " + r.getString(1) + " |");
            try{
                System.err.print(" Address: " + r.getString(2) + " |\n");
            } catch (SQLException sq){
                System.err.println();
            }
        }
        System.err.println();
    }

    public static void updateDep (float a, float b, Connection c) throws SQLException{
        PreparedStatement ps = c.prepareStatement("UPDATE professor SET department = ? WHERE department >= ? AND department < ?");
        ps.setFloat(1,b);
        ps.setFloat(2,a);
        ps.setFloat(3,a+1.0F);
        ps.execute();

        ps = c.prepareStatement("SELECT id, address FROM professor WHERE department = ?");
        ps.setFloat(1, b);
        ResultSet r = ps.executeQuery();
        printQuery(r);
        ps.close();
    }











    public static void main(String[] args) throws SQLException{
        long inizio, fine;
        System.out.println("Tubercolosi!");
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:postgresql://localhost/postgres";
        String username = "postgres";
        String password = "Luminita140403!";

        Connection con = DriverManager.getConnection(url, username, password);
        Statement stm = con.createStatement();
        ResultSet res;



        // -------- Es 2 ------- //

        stm.execute("CREATE TABLE professor(id INTEGER NOT NULL, name CHAR(50) NOT NULL, address CHAR(50) NOT NULL, age INTEGER NOT NULL, department FLOAT NOT NULL, PRIMARY KEY(id))");
        stm.execute("CREATE TABLE course(cid CHAR(25) NOT NULL, cname CHAR(50) NOT NULL, credits CHAR(30) NOT NULL, teacher INTEGER NOT NULL, PRIMARY KEY(cid), FOREIGN KEY(teacher) REFERENCES professor(id))");

        // ------- Es 3, 4 ------- //
        inizio = System.nanoTime();
        boolean exit = false;
        PreparedStatement pstm_prof = con.prepareStatement("INSERT INTO professor VALUES (?, ?, ?, ?, ?)");
        PreparedStatement pstm_cor = con.prepareStatement("INSERT INTO course VALUES (?, ?, ?, ?)");
        int i, id = 0, age, b;
        float department;
        String name, address, cid, cname, credits;

        for(i = 0; i < N / BatchDim; ++i){

            exit = false;

            while (exit == false){
                try{
                    for (int j = 0; j < BatchDim; j++){
                        id = randInt(N * 10);
                        age = randInt(100);
                        department = randFloat(N);
                        name = randString(50);
                        address = randString(50);
                        cname = randString(50);
                        credits = randString(30);
                        cid = randString(25);

                        pstm_prof.setInt(1, id);
                        pstm_prof.setString(2, name);
                        pstm_prof.setString(3, address);
                        pstm_prof.setInt(4, age);
                        pstm_prof.setFloat(5, department);
                        CheckExc(department, con);

                        pstm_cor.setString(1, cid);
                        pstm_cor.setString(2, cname);
                        pstm_cor.setString(3, credits);
                        pstm_cor.setInt(4, id);

                        pstm_prof.addBatch();
                        pstm_cor.addBatch();
                    }
                    pstm_prof.executeBatch();
                    pstm_cor.executeBatch();
                    exit = true;
                } catch ( SQLException sqlex ){
                    System.out.println(sqlex);
                } catch ( CustomExceptions cex ){
                    System.out.println(cex);
                }
            }
        }
        PreparedStatement upd = con.prepareStatement("UPDATE professor SET department = 1940.0 WHERE id = ?");
        upd.setInt(1, id);
        upd.execute();

        fine = System.nanoTime();
        System.out.println("Inserimento tuple: " + (fine - inizio) + " ns");

        // ------- Es 5 ------- //

        res = stm.executeQuery("SELECT id FROM professor");
        printQuery(res);

        // ------- Es 6 e 7 ------- //

        updateDep(1940.0F, 1973.0F, con);

        // ------- Es 8 ------- //

        stm.execute("CREATE INDEX ind ON professor USING btree ( department )");

        // ------- Es 9 ------- //

        res = stm.executeQuery("SELECT id FROM professor");
        printQuery(res);

        // ------- Es 1 ------- //

        try{
            stm.execute("DROP TABLE course");
        } catch (Exception e) {
            System.out.println("Course non esiste!");;
        }
        try{
            stm.execute("DROP TABLE professor");
        } catch (Exception e){
            System.out.println("Professor non esiste!");
        }



        // ------- close -------- //
        upd.close();
        pstm_prof.close();
        pstm_cor.close();
        res.close();
        stm.close();
        con.close();
    }
}

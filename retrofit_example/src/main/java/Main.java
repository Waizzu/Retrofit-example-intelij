import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class Main {
    public static void main (String [] args){
        System.out.println("Hello world!");
        HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor(); //depurar informacion en el logger
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)//le decimos que utilice el cliente creado arriba
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> repos = service.listRepos("Waizzu");
        //cuando hacemos una invocacion remota puede fallar
        try {
            List<Repo> result = repos.execute().body();//recibidmos json
            for (Repo r: result){
               System.out.println(r) ;
            }
        }catch(Exception e){
            System.out.println("EXCEPTION: ");
            System.out.println(e.toString());
        }
    }
}

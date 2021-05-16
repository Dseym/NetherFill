package tiktok.tiktok;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Video {
	
	public long shares, comments;
	
	public Video(String authorId, String videoId) {
        try {
        	String url = "https://www.tiktok.com/@%authorId%/video/%videoId%".replace("%authorId%", authorId).replace("%videoId%", videoId);
            Element script = Jsoup.connect(url).userAgent("Chrome").get().getElementById("__NEXT_DATA__");
            String scriptData = script.data();
            
    		JsonObject page = new JsonParser().parse(scriptData).getAsJsonObject().getAsJsonObject("props").getAsJsonObject("pageProps");
    		JsonObject stats = page.getAsJsonObject("itemInfo").getAsJsonObject("itemStruct").getAsJsonObject("stats");
    		
    		shares = stats.get("shareCount").getAsLong();
    		comments = stats.get("commentCount").getAsLong();
        } catch(IOException e) {}
	}
	
}

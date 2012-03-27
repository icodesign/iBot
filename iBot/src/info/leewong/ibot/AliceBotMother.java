package info.leewong.ibot;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.content.res.AssetManager;
import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.Context;
import bitoflife.chatterbean.parser.AliceBotParser;
import bitoflife.chatterbean.util.Searcher;

public class AliceBotMother
{
  
  private ByteArrayOutputStream gossip;
  
  public void setUp()
  {
    gossip = new ByteArrayOutputStream();
  }
  
  public String gossip()
  {
    return gossip.toString();
  }

  public AliceBot newInstance(Activity activity) throws Exception
  {
    Searcher searcher = new Searcher();
    AliceBotParser parser = new AliceBotParser();
    AssetManager am = activity.getAssets();
    FileUtil.creatFileDir(FileUtil.sdPath + "aiml/");
    AliceBot bot;
    if (FileUtil.isFileExist(FileUtil.sdPath + "aiml/general.aiml")){
    	new File(FileUtil.sdPath + "aiml/general.aiml").delete();
    }
	if (!FileUtil.isFileExist(FileUtil.sdPath + "aiml/general.aiml")){
    	FileUtil.write2SdCard(FileUtil.sdPath + "aiml/", "general.aiml", am.open("Bots/general.aiml"));
    }
	bot = parser.parse(am.open("Bots/context.xml"),
            am.open("Bots/splitters.xml"),
            am.open("Bots/substitutions.xml"),
            //searcher.search(FileUtil.sdPath+"aiml/", ".*\\.aiml"));
            searcher.search(FileUtil.sdPath+"aiml/", "general.aiml"));
            //am.open("Bots/m2.aiml"));
    Context context = bot.getContext(); 
    context.outputStream(gossip);
    return bot;
  }
}

package info.leewong.ibot;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SmsAdapter extends BaseAdapter{
	
	private Context context;
	private List<DetailEntity> list;
	public SmsAdapter(Context context,List<DetailEntity> list2){
		this.context=context;
		this.list=list2;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder=new ViewHolder();
		if(convertView==null){
			
			convertView = LayoutInflater.from(context).inflate(  
                    R.layout.item, null);  
			holder.text=(TextView) convertView.findViewById(R.id.TextView01);
			holder.icon_left=(ImageView)convertView.findViewById(R.id.iv_head_left);
			holder.icon_rignt=(ImageView)convertView.findViewById(R.id.iv_head_right);
			holder.rela=(RelativeLayout)convertView.findViewById(R.id.Rela);
			convertView.setTag(holder);
		}
		else{
			holder= (ViewHolder) convertView.getTag();
		}
		switch(list.get(position).getDiretion()){
		case Static.LEFT:
			holder.text.setBackgroundResource(R.drawable.chatfrom_bg_voice_pressed);
			holder.text.setText(list.get(position).getText());
			holder.icon_rignt.setVisibility(View.GONE);
			holder.icon_left.setVisibility(View.VISIBLE);
			holder.rela.setGravity(Gravity.LEFT);
			break;
		case Static.RIGHT:
			holder.text.setBackgroundResource(R.drawable.chatto_bg_voice_pressed);
			holder.text.setText(list.get(position).getText());
			holder.icon_left.setVisibility(View.GONE);
			holder.icon_rignt.setVisibility(View.VISIBLE);
			holder.rela.setGravity(Gravity.RIGHT);
			break;
		}		
		return convertView;
	}
	public static class ViewHolder{		
		ImageView icon_left;
		TextView text;
		ImageView icon_rignt;
		RelativeLayout rela;
	}
}

package ru.aviasales.template.ui.adapter;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ru.aviasales.core.search.object.GateData;
import ru.aviasales.template.R;
import ru.aviasales.template.proposal.ProposalManager;
import ru.aviasales.template.ui.view.AgencyItemView;

public class AgencySpinnerAdapter implements SpinnerAdapter {

	private final List<String> agencies;
	private final Map<String, GateData> gates;
	private OnAgencyClickListener onAgencyClickListener;

	public AgencySpinnerAdapter(List<String> agenciesCodes, Map<String, GateData> gates) {
		agencies = agenciesCodes;
		this.gates = gates;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_drop_down_view, parent, false);
			((TextView) convertView).setText(ProposalManager.getInstance().getAgencyName(agencies.get(position)));
		}

		return convertView;
	}

	@Override
	public View getDropDownView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.agency_item_layout, parent, false);
		}
		((AgencyItemView) convertView).setData(agencies.get(position),
				gates.get(agencies.get(position)).hasMobileVersion());
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onAgencyClickListener.onAgencyClick(((AgencyItemView) v).getAgency(), position);
			}
		});

		((AgencyItemView) convertView).setupTopAndBottomViews(position == 0, position == getCount() - 1);

		return convertView;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public int getCount() {
		return agencies.size();
	}

	@Override
	public String getItem(int position) {
		return agencies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	public void setOnAgencyClickListener(OnAgencyClickListener onAgencyClickListener) {
		this.onAgencyClickListener = onAgencyClickListener;
	}

	public interface OnAgencyClickListener {
		void onAgencyClick(String agency, int position);
	}
}

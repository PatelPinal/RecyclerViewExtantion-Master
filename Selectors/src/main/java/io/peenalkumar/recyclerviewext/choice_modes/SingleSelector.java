package io.peenalkumar.recyclerviewext.choice_modes;

import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
// TODO: 24-06-2022 Peenalkumar
/**
 * Tracks multiple selections, similarly to {@link AbsListView#CHOICE_MODE_SINGLE}. Calls to
 * {@link RecyclerView.Adapter#notifyItemChanged(int)} are done automatically.
 * <p>
 * Optionally, call {@link #bind(RecyclerView.ViewHolder, boolean)} from your
 * {@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)} to have the
 * {@link android.R.attr#state_activated} reflect the selected state.
 */
public class SingleSelector extends Selector {
    private long mSelectedId;
    private boolean mSelected;

    public SingleSelector(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.Adapter adapter) {
        super(recyclerView, adapter);
    }

    public void setSelected(long id, boolean selected) {
        long[] previousSelectedIds = getSelectedIds();
        boolean oldSelected = mSelected;
        long oldSelectedId = mSelectedId;

        mSelected = selected;
        if (mSelected) {
            mSelectedId = id;
        }

        if (mSelected != oldSelected || oldSelectedId != mSelectedId) {
            if (oldSelected) {
                notifyItemChanged(oldSelectedId);
            }
            if (mSelected) {
                notifyItemChanged(mSelectedId);
            }

            onSelectionChanged(getSelectedIds(), previousSelectedIds);
        }
    }

    public boolean isSelected(long id) {
        return mSelected && mSelectedId == id;
    }

    @Override
    public long[] getSelectedIds() {
        return mSelected ? new long[]{mSelectedId} : new long[0];
    }

    @Override
    public int getSelectedCount() {
        return mSelected ? 1 : 0;
    }

    @Override
    public void clearSelected() {
        long[] previousSelectedIds = getSelectedIds();
        boolean hadSelection = mSelected;

        mSelected = false;

        if (hadSelection) {
            notifyItemChanged(mSelectedId);

            onSelectionChanged(getSelectedIds(), previousSelectedIds);
        }
    }
}

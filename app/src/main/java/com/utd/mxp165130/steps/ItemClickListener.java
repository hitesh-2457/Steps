/**************************
 * Step Counter program
 * Class : CS6326.001
 * Spring 2018
 *
 * Coder 1:
 * 	Hitesh Gupta Tumsi Ramesh
 *   netId: hxg170230
 * Coder 2:
 * 	Meghana Pochiraju
 * 	netId: mxp165130
 *
 * This is a custom class that is used as onItem Click Listener on Recycler view.
 * It implements RecyclerView.OnItemTouchListener and the necessary methods of the class.
 *
 **************************/
package com.utd.mxp165130.steps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetector gestureDetector;

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Constructor to Instantiates a new Item click listener.
     *
     * @param context : the context
     * @param listener : the on item click listener
     **************************/
    public ItemClickListener(Context context, OnItemClickListener listener) {
        clickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    /**************************
     * Coder: Hitesh Gupta Tumsi Ramesh (hxg170230)
     *
     * Intercepts Touch event on the Recycler and overrides to open the Summary Activity.
     *
     * @param rv : the Recycler View on which the event is handled
     * @param e : the motion event object
     **************************/
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    /**************************
     * onTouchEvent handler, Unused
     *
     * @param rv : the Recycler View on which the event is handled
     * @param e : the motion event object
     **************************/
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    /**************************
     * onRequestDisallowInterceptTouchEvent handler, Unused
     *
     * @param disallowIntercept
     **************************/
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**************************
     * The interface On item click listener.
     **************************/
    public interface OnItemClickListener {
        /**************************
         * On item click deceleration.
         *
         * @param view     the view
         * @param position the position
         **************************/
        void onItemClick(View view, int position);
    }
}

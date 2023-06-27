package com.sendpost.dreamsoft.ImageEditor.Frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.sendpost.dreamsoft.Fragments.ContactFragment
import com.sendpost.dreamsoft.viewmodel.UserViewModel
import com.sendpost.dreamsoft.R
import kotlinx.android.synthetic.main.fragment_sticker.*


class PersonalFragment(list: ArrayList<com.sendpost.dreamsoft.model.FrameModel>, frameListner: FrameListener) : Fragment() {

    private var rvPersonalFrame: RecyclerView? = null
    private var frame_list = ArrayList<com.sendpost.dreamsoft.model.FrameModel>()
    var frameListner = frameListner

    var userViewModel: UserViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sticker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPersonalFrame = view.findViewById(R.id.rvEmoji)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java);
        userViewModel!!.getUserFrames(com.sendpost.dreamsoft.Classes.Functions.getUID(context))
            .observe(viewLifecycleOwner, object : Observer<com.sendpost.dreamsoft.responses.UserResponse> {
                override fun onChanged(response: com.sendpost.dreamsoft.responses.UserResponse?) {
                    if (response != null) {
                        if (response.userframes.size > 0) {
                            for (i in 0 until response.userframes.size) {
                                val frameModel =
                                    com.sendpost.dreamsoft.model.FrameModel()
                                frameModel.id = response.userframes.get(i).id;
                                frameModel.title = response.userframes.get(i).id;
                                frameModel.type = "user";
                                frameModel.thumbnail = response.userframes.get(i).item_url
                                frame_list.add(frameModel)
                            }
                            rvPersonalFrame?.adapter =
                                com.sendpost.dreamsoft.ImageEditor.Frame.FrameAdapter(
                                    activity,
                                    frame_list,
                                    object : FrameListener {
                                        override fun onFrameSelected(name: com.sendpost.dreamsoft.model.FrameModel?) {
                                            frameListner?.onFrameSelected(name)
                                        }
                                    })
                            no_frame_found_tv?.visibility = View.GONE
                        } else {
                            no_frame_found_tv?.visibility = View.VISIBLE
                        }
                    }
                }
            })
        get_frame.setOnClickListener{
            showContactFragment()
        }
    }
    private fun showContactFragment() {
        val transaction = requireFragmentManager().beginTransaction()
        transaction.setCustomAnimations(
            R.anim.in_from_bottom,
            R.anim.out_to_top,
            R.anim.in_from_top,
            R.anim.out_from_bottom
        )
        transaction.addToBackStack(null)
        transaction.replace(android.R.id.content,
            ContactFragment()
        ).commit()
    }
}
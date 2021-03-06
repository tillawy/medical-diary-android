package com.tillawy.medical_diary.android.medical_diary

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.*
import com.tillawy.medical_diary.android.medical_diary.extensions.onChange
import kotlinx.android.synthetic.main.fragment_form.*
import io.realm.Realm
import kotlin.properties.Delegates
import io.realm.kotlin.where
import com.tillawy.medical_diary.android.medical_diary.models.Patient
import io.realm.kotlin.createObject
import java.text.SimpleDateFormat
import java.util.*
import android.view.MenuInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter










/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FormFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

/*
https://github.com/realm/realm-java/blob/master/examples/kotlinExample/src/main/kotlin/io/realm/examples/kotlin/KotlinExampleActivity.kt
 */
class FormFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
        realm = Realm.getDefaultInstance()

        if (realm.where<Patient>().count().toInt() == 0){
            realm.executeTransaction {
                val patient = realm.createObject<Patient>()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realm = Realm.getDefaultInstance()
        val patient = realm.where<Patient>().findFirst()!!

        editTextFirstName.setText(patient.firstName)
        editTextFirstName.onChange { it1 : String ->
            realm.executeTransaction {
                patient.firstName = it1
            }
        }

        editTextFatherName.setText(patient.fatherName)
        editTextFatherName.onChange { it1 : String ->
            realm.executeTransaction {
                patient.fatherName = it1
            }
        }

        editTextLastName.setText(patient.lastName)
        editTextLastName.onChange { it1 : String ->
            realm.executeTransaction {
                patient.lastName = it1
            }
        }

        val age = if (patient.age != null) "${patient.age}" else ""
        editTextAge.setText(age)
        editTextAge.onChange { it1: String ->
            realm.executeTransaction {
                patient.age = it1.toInt()
            }
        }

        checkBoxIsOrganDonor.isChecked = patient.isOrganDonor
        checkBoxIsOrganDonor.setOnCheckedChangeListener {compoundButton, isChecked ->
            realm.executeTransaction {
                patient.isOrganDonor = isChecked
            }
        }

        displayBirthDate(patient)

        val cal = Calendar.getInstance()

        buttonEditBirthDate.setOnClickListener {
            cal.time = patient.birthDate ?: Date()
            val dateDialogue = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                realm.executeTransaction {
                    val date = GregorianCalendar(year, monthOfYear, dayOfMonth).time
                    patient.birthDate = date
                    displayBirthDate(patient)
                }

            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            dateDialogue.show()

        }


        buttonBloodType.setOnClickListener(View.OnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("Welcome")
            alertDialogBuilder.setItems(R.array.blood_types, DialogInterface.OnClickListener { dialog, whichButton ->
                realm.executeTransaction {
                    patient.bloodType = whichButton
                    displayBloodType(patient)
                }
            })
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        })
        displayBloodType(patient)

    }


    override fun onContextItemSelected(item: MenuItem?): Boolean {
        var info = item?.menuInfo as AdapterView.AdapterContextMenuInfo
        println( "order: ${info.position}" )
        return super.onContextItemSelected(item)
    }

    fun displayBloodType(patient: Patient){
        var blood_types = getResources().getStringArray(R.array.blood_types);
        buttonBloodType.setText("Blood type: ${blood_types[patient.bloodType]}")
    }
    fun displayBirthDate(patient: Patient){

        val format = SimpleDateFormat("dd/MM/yyy")
        if (patient.birthDate != null) {
            buttonEditBirthDate.text = format.format(patient.birthDate)
        }
        val cal = Calendar.getInstance()
        cal.time = patient.birthDate ?: Date()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    fun onEdiBirthDateButtonClicked(v: View){

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FormFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): FormFragment {
            val fragment = FormFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor

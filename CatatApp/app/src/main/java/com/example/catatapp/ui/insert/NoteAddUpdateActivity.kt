package com.example.catatapp.ui.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catatapp.R
import com.example.catatapp.ViewModelFactory
import com.example.catatapp.database.Note
import com.example.catatapp.databinding.ActivityNoteAddUpdateBinding

class NoteAddUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var note: Note? = null

    //ViewModel dengan Activity
    private lateinit var noteeAddUpdateViewModel: NoteAddUpdateViewModel

    private var _activityNotAddUpdateBinding: ActivityNoteAddUpdateBinding? = null
    private val binding get() = _activityNotAddUpdateBinding
    ///

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ViewModel dengan Activity
        _activityNotAddUpdateBinding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        noteeAddUpdateViewModel = obtainViewModel(this@NoteAddUpdateActivity)
        ///

        //menambahkan, memperbarui, dan menghapus item
        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle: String
        val btnTitle: String

        if(isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null) {
                note?.let { note ->
                    binding?.edtTitle?.setText(note.title)
                    binding?.edtDescription?.setText(note.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnSubmit?.text = btnTitle
        ///

        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.
        }
    }

    //ViewModel dengan Activity
    override fun onDestroy() {
        super.onDestroy()
        _activityNotAddUpdateBinding = null
    }


    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }
    ///
}
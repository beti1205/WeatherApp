package com.example.weatherapp.ui.favouritecity

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import com.example.weatherapp.R

class CityActionMode(private val onDelete: () -> Unit) : ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        val inflater = mode?.menuInflater
        inflater?.inflate(
            R.menu.contextual_action_bar,
            menu
        )
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete) {
            onDelete()
        }

        mode?.finish()
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
    }
}
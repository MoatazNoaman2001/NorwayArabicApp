package com.example.norwayapplicationxml.common

import androidx.navigation.fragment.FragmentNavigator
import com.example.norwayapplicationxml.domain.model.New

interface ClickListener {
    fun onClick(extras: FragmentNavigator.Extras, link: New)
}
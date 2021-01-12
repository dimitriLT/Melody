package com.dimitri.le.torriellec.melody.ui.common

abstract class BaseUiMapper<DOMAIN, UI> {

    abstract fun toUi(domain: DOMAIN): UI
}
package com.dimitri.le.torriellec.melody.repository.mapper

abstract class BaseDomainMapper<API, DOMAIN> {

    abstract fun toDomain(api: API): DOMAIN
}
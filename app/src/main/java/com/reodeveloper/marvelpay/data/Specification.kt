package com.reodeveloper.marvelpay.data

// This provides a common ancestor
interface Specification {
    // To be defined on the children
}

class SpecificationBySelected : Specification
class SpecificationByPage(val page: Int) : Specification
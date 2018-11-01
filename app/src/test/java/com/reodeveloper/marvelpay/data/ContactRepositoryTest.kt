package com.reodeveloper.marvelpay.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.reodeveloper.marvelpay.data.device.DeviceContactDataSource
import com.reodeveloper.marvelpay.data.retrofit.RetrofitDataSource
import com.reodeveloper.marvelpay.data.room.RoomDataSource
import com.reodeveloper.marvelpay.domain.model.Contact
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class ContactRepositoryTest {

    private lateinit var contactRepository: ContactRepository

    private lateinit var apiDataSource: RetrofitDataSource
    private lateinit var deviceDataSource: DeviceContactDataSource
    private lateinit var roomDataSource: RoomDataSource

    companion object {
        private const val ANY_NAME = "John Smith"
        private const val ANY_PHONE = "987 654 321"
        private const val ANY_IMAGE = "http://image.awesome.jpg"

        private const val ANY_BOOL = false
    }

    @Before
    fun setUp() {
        apiDataSource = mock()
        deviceDataSource = mock()
        roomDataSource = mock()
        contactRepository = ContactRepository(apiDataSource, deviceDataSource)
    }

    @Test
    fun shouldGetAllData() {
        Mockito.`when`(apiDataSource.getAll()).thenReturn(givenAnyApiContactList())
        Mockito.`when`(deviceDataSource.getAll()).thenReturn(givenAnyDbContactList())

        val list = contactRepository.getAll()

        verify(apiDataSource).getAll()
        verify(deviceDataSource).getAll()
        Assert.assertEquals(givenAnyDbContactList().size + givenAnyApiContactList().size, list.size)
    }

    @Test fun shouldNotStoreBecauseNoCache(){
        contactRepository.cache = null
        contactRepository.store(givenAnyContactList())

        val list = contactRepository.getAll()

        Assert.assertEquals(0, list.size)
    }

    @Test fun shouldStoreBecauseThereIsCache(){
        Mockito.`when`(deviceDataSource.getAll()).thenReturn(givenAnyDbContactList())
        contactRepository.cache = roomDataSource
        contactRepository.store(givenAnyContactList())

        val list = contactRepository.getAll()

        Assert.assertEquals(3, list.size)
    }

    private fun givenAnyContactList() = Contact(ANY_NAME, ANY_PHONE, ANY_IMAGE, ANY_BOOL)

    private fun givenAnyDbContactList() =
        arrayListOf(givenAnyContactList(), givenAnyContactList(), givenAnyContactList())

    private fun givenAnyApiContactList() = arrayListOf(givenAnyContactList(), givenAnyContactList())

}
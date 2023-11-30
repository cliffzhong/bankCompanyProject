package com.project.bankcompany.daoimpl.springdatajpa;

import com.project.bankcompany.daoimpl.repository.CheckingAccountRepository;
import com.project.bankcompany.daoimpl.sproingdatajpa.CheckingAccountDaoSpringDataJPAImpl;
import com.project.bankcompany.entity.Appointment;
import com.project.bankcompany.entity.CheckingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CheckingAccountDaoSpringDataJPAImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @InjectMocks
    private CheckingAccountDaoSpringDataJPAImpl checkingAccountDao;

    @Mock
    private CheckingAccountRepository checkingAccountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCheckingAccount() {
        CheckingAccount mockedCheckingAccount = mock(CheckingAccount.class);

        when(checkingAccountRepository.save(mockedCheckingAccount)).thenReturn(mockedCheckingAccount);

        CheckingAccount savedCheckingAccount = checkingAccountDao.save(mockedCheckingAccount);

        verify(checkingAccountRepository, times(1)).save(mockedCheckingAccount);

        assertNotNull(savedCheckingAccount);
    }

    @Test
    public void testUpdateCheckingAccount(){
        CheckingAccount mockedCheckingAccount = mock(CheckingAccount.class);

        when(checkingAccountRepository.save(mockedCheckingAccount)).thenReturn(mockedCheckingAccount);

        CheckingAccount updatedCheckingAccount = checkingAccountDao.update(mockedCheckingAccount);

        verify(checkingAccountRepository,times(1)).save(mockedCheckingAccount);

        assertNotNull(updatedCheckingAccount);

    }

    @Test
    public void testDeleteById(){
        Long checkingAccountId = 1L;

        doNothing().when(checkingAccountRepository).deleteById(checkingAccountId);

        boolean successFlag = checkingAccountDao.deleteById(checkingAccountId);

        verify(checkingAccountRepository,times(1)).deleteById(checkingAccountId);

        assertTrue(successFlag);

    }

    @Test
    public void testDelete(){
        CheckingAccount mockedCheckingAccount = mock(CheckingAccount.class);

        doNothing().when(checkingAccountRepository).delete(mockedCheckingAccount);

        boolean deleteResult = checkingAccountDao.delete(mockedCheckingAccount);

        verify(checkingAccountRepository, times(1)).delete(mockedCheckingAccount);

        assertTrue(deleteResult);
    }

    @Test
    public void testFindAllCheckingAccount(){
        List<CheckingAccount> spyCheckingAccountList = new ArrayList<>();

        CheckingAccount checkingAccountOne = spy(new CheckingAccount());
        CheckingAccount checkingAccountTwo = spy(new CheckingAccount());
        CheckingAccount checkingAccountThree = spy(new CheckingAccount());

        spyCheckingAccountList.add(checkingAccountOne);
        spyCheckingAccountList.add(checkingAccountTwo);
        spyCheckingAccountList.add(checkingAccountThree);

        when(checkingAccountRepository.findAll()).thenReturn(spyCheckingAccountList);

        List<CheckingAccount> retrivedlist = checkingAccountDao.findAllCheckingAccount();

        verify(checkingAccountRepository,times(1)).findAll();
        assertEquals(3, spyCheckingAccountList.size());

    }

    @Test
    public void testFindById(){
        CheckingAccount mockedCheckingAccount = mock(CheckingAccount.class);

        Long checkingAccountId = 1L;

        when(checkingAccountRepository.findById(checkingAccountId)).thenReturn(Optional.of(mockedCheckingAccount));

        CheckingAccount checkingAccount = checkingAccountDao.findCheckingAccountById(checkingAccountId);

        verify(checkingAccountRepository,times(1)).findById(checkingAccountId);

        assertNotNull(checkingAccount);
        assertEquals(mockedCheckingAccount, checkingAccount);
    }
}

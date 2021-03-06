package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
import uo.ri.cws.application.persistence.invoice.ChargeRecord;
import uo.ri.cws.application.business.paymentmean.PaymentMeanForInvoicingDto;

public class InvoiceGatewayImpl implements InvoiceGateway {

	private String SQL = "";
	// Process
	private Connection c = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private Conf conf = new Conf();

	@Override
	public void add(InvoiceRecord t) {
		try {
			c = Jdbc.getConnection();
			SQL = conf.getProperty("INVOICE_ADD");
			pst = c.prepareStatement(SQL);
			pst.setString(1, t.id);
			pst.setString(2, "" + t.total);
			pst.setString(3, "" + t.vat);
			pst.setString(4, "" + t.number);
			pst.setString(5, "" + t.date);
			pst.setString(6, t.status);
			pst.setString(7, "" + t.usedForVoucher);
			pst.executeUpdate();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public void remove(String id) {
		try {
			c = Jdbc.getConnection();
			SQL = conf.getProperty("INVOICE_REMOVE");
			pst = c.prepareStatement(SQL);
			pst.setString(1, id);
			pst.executeUpdate();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void update(InvoiceRecord t) {
		try {
			c = Jdbc.getConnection();
			SQL = conf.getProperty("INVOICE_UPDATE");
			pst = c.prepareStatement(SQL);
			pst.setString(1, t.id);
			pst.setString(2, "" + t.total);
			pst.setString(3, "" + t.vat);
			pst.setString(4, "" + t.number);
			pst.setString(5, "" + t.date);
			pst.setString(6, "" + t.status);
			pst.setString(7, "" + t.usedForVoucher);
			pst.executeUpdate();

		} catch (PersistenceException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst, c);
		}

	}

	@Override
	public Optional<InvoiceRecord> findById(String id) {
		Optional<InvoiceRecord> invoice = Optional.ofNullable(new InvoiceRecord());
		try {
			c = Jdbc.getConnection();
			SQL = conf.getProperty("INVOICE_FIND_BY_ID");
			pst = c.prepareStatement(SQL);
			pst.setString(1, id);
			rs = pst.executeQuery();

			invoice = RecordAssembler.toInvoiceRecord(rs);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, pst);
		}
		return invoice;
	}

	@Override
	public Optional<InvoiceRecord> findByNumber(Long number) {
		Optional<InvoiceRecord> invoice = Optional.ofNullable(new InvoiceRecord());
		try {
			c = Jdbc.getConnection();
			SQL = conf.getProperty("INVOICE_FIND_BY_NUMBER");
			pst = c.prepareStatement(SQL);
			pst.setString(1, id);
			rs = pst.executeQuery();

			invoice = RecordAssembler.toInvoiceRecord(rs);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, pst);
		}
		return invoice;
	}

	@Override
	public Long getNextInvoiceNumber() throws PersistenceException {
		Long number = 1;
		try {
			c = Jdbc.getConnection();
 			SQL = conf.getProperty("INVOICE_GET_NEXT_INVOICE_NUMBER");
			pst = c.prepareStatement(SQL);
			rs = pst.executeQuery();
			number += (int) (rs.getInt(0));
  
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, pst);
		}
		if(number!=1)
			return number;

		return null;
	}

	@Override
	public List<InvoiceRecord> findAll() {
		List<InvoiceRecord> invoices = new ArrayList<InvoiceRecord>();
		try {
			c = Jdbc.getConnection();
			SQL = conf.getProperty("INVOICE_FIND_ALL");
			pst = c.prepareStatement(SQL);

			rs = pst.executeQuery();

			invoices = RecordAssembler.toInvoiceRecordList(rs);
			return invoices;
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Jdbc.close(rs, pst);
		}
		return invoices;

	}


 	public Optional<InvoiceRecord> findInvoiceByNumber(Long number) throws PersistenceException{
		return null;
	}

	public List<PaymentMeanForInvoicingRecord> findPayMeansByClientDni(String dni)throws PersistenceException{
		return null;
	}

	public void settleInvoice(String invoiceId, List<ChargeRecord> charges)throws PersistenceException{
		return;	
						    
	}
}

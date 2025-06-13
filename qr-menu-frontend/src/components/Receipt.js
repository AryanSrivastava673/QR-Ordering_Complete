import React from 'react';
import { useLocation } from 'react-router-dom';
import './Receipt.css';  // Make sure to create this CSS file

function Receipt() {
    const location = useLocation();
    const receipt = location.state.receipt;

    return (
        <div className="receipt-container">
            <div className="receipt-header">
                <h1>Restaurant Receipt</h1>
            </div>
            <div className="receipt-body">
                {receipt ? (
                    <>
                        <p className="receipt-info">Receipt ID: {receipt.id}</p>
                        <div className="receipt-items">
                            {receipt.receiptItemDTOList.map(item => (
                                <div key={item.id} className="item-row">
                                    <span>{item.menuItemName}</span>
                                    <span>x {item.quantity}</span>
                                </div>
                            ))}
                        </div>
                        <div className="receipt-totals">
                            <p>Items Total: ${receipt.itemsTotal.toFixed(2)}</p>
                            <p>Tax: ${receipt.tax.toFixed(2)}</p>
                            <h3>Grand Total: ${receipt.grandTotal.toFixed(2)}</h3>
                        </div>
                    </>
                ) : <p>Loading receipt details...</p>}
            </div>
            <div className="receipt-footer">
                <p>Thank you for visiting!</p>
            </div>
        </div>
    );
}

export default Receipt;
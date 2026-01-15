import React, { useState } from 'react';
import axios from 'axios';
import { useNotification } from '../context/NotificationContext'
import './AddToCartButton.css';

const AddToCartButton = ({ item, tableNo }) => {
    const { notify } = useNotification();
    const [added, setAdded] = useState(false);

    const addToCart = async () => {
        try {
            const response = await axios.post(`http://localhost:8084/api/${tableNo}/addToCart`, item);
            console.log('Item added to cart:', response.data);
            setAdded(true);
            notify({ message: 'Added to cart', type: 'success' });
            setTimeout(() => setAdded(false), 1200);
        } catch (error) {
            console.error('Error adding item to cart:', error);
            notify({ message: 'Failed to add item to cart.', type: 'error' });
        }
    };

    return (
        <button
            className={`add-to-cart-btn${added ? ' added' : ''}`}
            onClick={addToCart}
            disabled={added}
        >
            {added ? 'Added!' : 'Add to Cart'}
        </button>
    );
};

export default AddToCartButton;
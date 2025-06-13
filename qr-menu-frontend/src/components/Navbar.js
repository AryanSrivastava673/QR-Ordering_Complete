import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useTableContext } from '../context/TableContext';
import { useLocation, useNavigate } from 'react-router-dom';

const Navbar = () => {
  const [categories, setCategories] = useState([]);
  const [menuOpen, setMenuOpen] = useState(false);
  const { tableNo } = useTableContext();
  const location = useLocation(); // Get location object
  const navigate = useNavigate(); // Get navigate function

  useEffect(() => {
    // If not on the admin page, fetch categories
    if (location.pathname !== '/admin') {
      axios.get(`http://localhost:8084/api/categories`)
        .then(response => {
          setCategories(response.data);
        })
        .catch(error => console.error('Error fetching categories:', error));
    } else {
      // Clear categories if on the admin page
      setCategories([]);
    }
  }, [tableNo, location.pathname]); // Include location.pathname in dependency array

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  };

  const scrollToCategory = (categoryName) => {
    const categoryElement = document.getElementById(categoryName.replace(/\s+/g, ''));
    const navbarHeight = document.querySelector('nav').offsetHeight;

    if (categoryElement) {
      const elementPosition = categoryElement.offsetTop;
      const offsetPosition = elementPosition - navbarHeight - 10;

      window.scrollTo({
        top: offsetPosition,
        behavior: 'smooth'
      });
    }
    setMenuOpen(false);
  };

  const goToAdminTablesPage = () => navigate("/Admin");
  const goToAdminAllOrdersPage = () => navigate("/Admin/all-orders");
  const goToAdminReceiptsPage = () => navigate("/Admin/receipts");

  return (
    <nav>
      <h1 onClick={scrollToTop}>Mocha Muse</h1>
      {/* Conditionally render categories based on path */}
      {location.pathname !== '/Admin' && location.pathname !== '/Receipt' && location.pathname !== '/Admin/all-orders' &&(
        <ul className={menuOpen ? 'active' : ''}>
          {categories.map(category => (
            <li key={category.id} onClick={() => scrollToCategory(category.name)}>
              {category.name}
            </li>
          ))}
        </ul>
      )}
      {(location.pathname === '/Admin' || location.pathname === '/Receipt'|| location.pathname === '/Admin/all-orders') && (
        <ul className={menuOpen ? 'active' : ''}>
          {/* {categories.map(category => (
            <li key={category.id} onClick={() => scrollToCategory(category.name)}>
              {category.name}
            </li>
          ))} */}
           <li key="tables_nav" onClick={goToAdminTablesPage}>
            Tables
          </li>
          <li key="orders_nav" onClick={goToAdminAllOrdersPage}>
            Pending Orders
          </li>
          <li key="receipts_nav" onClick={goToAdminReceiptsPage}>
            Receipts
          </li>
        </ul>
      )}
      <div className={`hamburger ${menuOpen ? 'active' : ''}`} onClick={toggleMenu}>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </nav>
  );
}

export default Navbar;
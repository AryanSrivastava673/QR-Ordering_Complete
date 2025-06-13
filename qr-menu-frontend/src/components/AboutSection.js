import React, { useEffect } from 'react';
//import './AboutSection.css';

const AboutSection = () => {
  useEffect(() => {
    const handleScroll = () => {
      const scrollY = window.scrollY;
      const blurValue = Math.min(5, 2 + 0.1 * scrollY / 10); // Increases blur as you scroll, max out at 5
      document.querySelector('.about-background').style.filter = `blur(${blurValue}px)`;
    };

    window.addEventListener('scroll', handleScroll);

    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <section className="about-container">
      <div className="about-background"></div> {/* Background layer */}
      <div className="about-content">
        <h1>Welcome to Mocha Muse!</h1>
        <p>Located in the heart of the city, we serve freshly brewed coffee and a selection of artisan pastries. Enjoy a warm, inviting atmosphere with vintage décor that takes you back in time.</p>
        {/* <a href="#menu" className="btn">Explore Our Menu</a> */}
      </div>
    </section>
  );
}

export default AboutSection;
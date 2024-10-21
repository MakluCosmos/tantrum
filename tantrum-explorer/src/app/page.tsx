'use client'

import Button from '@mui/material/Button';
import Image from "next/image";
import { useRouter } from 'next/navigation';
import styles from "./page.module.css";

export default function Home() {
  const router = useRouter();
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <Image
          src={`/remy_tatum.jpeg`}
          alt="Remy LeBeau"
          width={210}
          height={250}
          priority
        />
        <div className="content-container">


        <div className="welcome-container">
          <div> Welcome to Tantrum Explorer</div>
          <div> Explore cloud resource information with an easy click</div>
        </div>

          <Button variant="contained" color='secondary' onClick={() => router.push('/lookup')}>Dive in</Button>
        </div>
      </main>
      <footer className="footer">
        <div>Developer: Marcel K.</div>
        <div>Assginment for: Tatum Security</div>
      </footer>
    </div>
  );
}

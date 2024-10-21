
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import CssBaseline from "@mui/material/CssBaseline";
import Link from "next/link";
import * as React from "react";
import CustomForm from "./form";

export default async function LookupForm() {
  const ip = 'http://localhost:8080/cloud-providers';
  console.log("FETCHING DATA FROM: ", ip);
  const res = await fetch(ip);  // Await the fetch call

  // Log status and headers early to check if the request completes
  console.log("STATUS: ", res.status);
  console.log("HEADERS: ", res.headers);
  if (!res.ok) {
    throw new Error("Failed to fetch data");
  }
  // const providers = await data.json();
  return (
    <React.Fragment>
      <CssBaseline />
      <Container fixed className="lookup-form-container">
        <CustomForm providers={'DONE'}></CustomForm>
        <div className="action-container">
          <Link href='/lookup/result'><Button sx={{minWidth: 150, maxWidth: 250}} variant="contained" color="primary">
            Fetch List
          </Button>
          </Link>
        </div>
      </Container>
    </React.Fragment>
  );
}

/**
 * import { ThemeOptions } from '@mui/material/styles';

export const themeOptions: ThemeOptions = {
  palette: {
    mode: 'light',
    primary: {
      main: '#292F36',
    },
    secondary: {
      main: '#F90093',
    },
    background: {
      default: '#EBCFB2',
      paper: '#fafafa',
    },
    text: {
      primary: 'rgba(72,81,91,0.87)',
      secondary: 'rgba(72,81,91,0.6)',
      disabled: 'rgba(72,81,91,0.3)',
    },
  },
};
 */

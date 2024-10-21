"use client";

import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import * as React from "react";


// export default function CustomForm(providers: any) {
export default function CustomForm({ providers }) {
  const [cloudProvider, setCloudProvider] = React.useState("");
  const handleChange = (event: SelectChangeEvent) => {
    setCloudProvider(event.target.value);
  };
  console.log(providers);
  return (
        <div className="form-container">
          <FormControl sx={{ minWidth: 200, maxWidth: 300 }} size="small">
            <InputLabel id="cloud-provider-select">Cloud Provider</InputLabel>
            <Select
              labelId="cloud-provider-select"
              value={cloudProvider}
              label="Cloud Provider"
              onChange={handleChange}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value={10}>GCP</MenuItem>
              <MenuItem value={20}>AWS</MenuItem>
            </Select>
          </FormControl>
            <FormControl sx={{ minWidth: 200, maxWidth: 300 }} size="small">
              <InputLabel id="resource-type-label">Resource Type</InputLabel>
              <Select
                disabled={cloudProvider === ''}
                labelId="resource-type-label"
                value={cloudProvider}
                label="Resource Type"
                onChange={handleChange}
              >
                <MenuItem value="">
                  <em>None</em>
                </MenuItem>
                <MenuItem value={10}>Ten</MenuItem>
                <MenuItem value={20}>Twenty</MenuItem>
                <MenuItem value={30}>Thirty</MenuItem>
              </Select>
            </FormControl>
        </div>
  );
}
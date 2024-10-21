"use client"
import CircularProgress, {
    circularProgressClasses,
    CircularProgressProps,
} from '@mui/material/CircularProgress';
import * as React from "react";

export default function Loading(props: CircularProgressProps) {
  return (
    <React.Fragment>
      <CircularProgress
        variant="indeterminate"
        disableShrink
        sx={(theme) => ({
          color: '#1a90ff',
          animationDuration: '550ms',
          position: 'absolute',
          left: 0,
          [`& .${circularProgressClasses.circle}`]: {
            strokeLinecap: 'round',
          },
          ...theme.applyStyles('dark', {
            color: '#308fe8',
          }),
        })}
        size={40}
        thickness={4}
        {...props}
      />
    </React.Fragment>
  );
}
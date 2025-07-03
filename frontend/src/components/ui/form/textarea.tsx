import * as React from 'react';
import { UseFormRegisterReturn } from 'react-hook-form';

import { cn } from '@/utils/cn';

import { FieldWrapper, FieldWrapperPassThroughProps } from './field-wrapper';
import { Textarea as CatalystTextarea } from '@/components/catalyst/textarea';

export type TextareaProps = React.TextareaHTMLAttributes<HTMLTextAreaElement> &
  FieldWrapperPassThroughProps & {
    className?: string;
    registration: Partial<UseFormRegisterReturn>;
  };

const Textarea = React.forwardRef<HTMLTextAreaElement, TextareaProps>(
  ({ className, label, error, registration, ...props }, ref) => {
    return (
      <FieldWrapper label={label} error={error}>
        <CatalystTextarea
          className={cn(className)}
          ref={ref}
          {...registration}
          {...props}
        />
      </FieldWrapper>
    );
  },
);
Textarea.displayName = 'Textarea';

export { Textarea };

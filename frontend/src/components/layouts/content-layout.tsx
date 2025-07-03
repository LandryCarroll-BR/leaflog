import * as React from 'react';

import { Heading } from '@/components/catalyst/heading';

import { Head } from '../seo';

type ContentLayoutProps = {
  children: React.ReactNode;
  title: string;
  action?: React.ReactNode;
};

export const ContentLayout = ({
  children,
  title,
  action,
}: ContentLayoutProps) => {
  return (
    <>
      <Head title={title} />
      <div className="py-6">
        <div className="mx-auto max-w-7xl flex items-center justify-between border-b border-gray-200 pb-4">
          <Heading level={1}>{title}</Heading>
          {action}
        </div>
        <div className="mx-auto max-w-7xl py-6">{children}</div>
      </div>
    </>
  );
};

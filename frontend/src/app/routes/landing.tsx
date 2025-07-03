import { useNavigate } from 'react-router';

import logo from '@/assets/logo.svg';
import { Head } from '@/components/seo';
import { paths } from '@/config/paths';
import { Button } from '@/components/catalyst/button';

const LandingRoute = () => {
  const navigate = useNavigate();

  const handleStart = () => {
    navigate(paths.app.dashboard.getHref());
  };

  return (
    <>
      <Head description="Welcome to bulletproof react" />
      <div className="flex h-screen items-center bg-white">
        <div className="mx-auto max-w-7xl px-4 py-12 text-center sm:px-6 lg:px-8 lg:py-16">
          <div className="flex gap-4 items-center justify-center">
            <img src={logo} alt="react" />
            <h2 className="text-2xl font-extrabold tracking-tight text-gray-900">
              Leaf Log
            </h2>
          </div>
          <div className="mt-8 flex justify-center">
            <div className="inline-flex rounded-md shadow">
              <Button onClick={handleStart}>Get started</Button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default LandingRoute;

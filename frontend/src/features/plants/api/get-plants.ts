import { queryOptions, useQuery } from '@tanstack/react-query';

import { api } from '@/lib/api-client';
import { QueryConfig } from '@/lib/react-query';
import { Plant } from '@/types/api';

export const getPlants = (): Promise<Plant[]> => {
  return api.get(`/plants`);
};

export const getPlantsQueryOptions = () => {
  return queryOptions({
    queryKey: ['plants'],
    queryFn: () => getPlants(),
  });
};

type UsePlantsOptions = {
  queryConfig?: QueryConfig<typeof getPlantsQueryOptions>;
};

export const usePlants = ({ queryConfig }: UsePlantsOptions) => {
  return useQuery({
    ...getPlantsQueryOptions(),
    ...queryConfig,
  });
};
